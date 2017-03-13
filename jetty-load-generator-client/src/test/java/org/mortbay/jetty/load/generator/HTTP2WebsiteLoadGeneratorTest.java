package org.mortbay.jetty.load.generator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.HdrHistogram.AtomicHistogram;
import org.HdrHistogram.Histogram;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.toolchain.perf.HistogramSnapshot;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.load.generator.resource.Resource;

public class HTTP2WebsiteLoadGeneratorTest extends WebsiteLoadGeneratorTest {
    @Before
    public void prepare() throws Exception {
    }

    @Test
    public void testHTTP2WithoutPush() throws Exception {
        prepareServer(new HTTP2ServerConnectionFactory(new HttpConfiguration()), new TestHandler());
        testHTTP2();
    }

    @Test
    public void testHTTP2WithPush() throws Exception {
        prepareServer(new HTTP2ServerConnectionFactory(new HttpConfiguration()), new PushingHandler());
        testHTTP2();
    }

    private void testHTTP2() {
        MonitoringThreadPoolExecutor executor = new MonitoringThreadPoolExecutor(1024, 60, TimeUnit.SECONDS);

        AtomicLong requests = new AtomicLong();
        Histogram histogram = new AtomicHistogram(TimeUnit.MICROSECONDS.toNanos(1), TimeUnit.SECONDS.toNanos(10), 3);
        LoadGenerator loadGenerator = prepareLoadGenerator(new HTTP2ClientTransportBuilder())
                .warmupIterationsPerThread(10)
                .iterationsPerThread(100)
//                .runFor(2, TimeUnit.MINUTES)
                .usersPerThread(100)
                .channelsPerUser(1000)
                .resourceRate(20)
                .executor(executor)
                .resourceListener((Resource.TreeListener)info -> histogram.recordValue(info.getTotalTime() - info.getRequestTime()))
                .requestListener(new Request.Listener.Adapter() {
                    @Override
                    public void onQueued(Request request) {
                        requests.incrementAndGet();
                    }
                })
                .requestListener(new Request.Listener.Adapter() {
                    @Override
                    public void onBegin(Request request) {
                        requests.decrementAndGet();
                    }
                })
                .build();

        loadGenerator.begin().join();

        HistogramSnapshot snapshot = new HistogramSnapshot(histogram, 20, "load time", "us", TimeUnit.NANOSECONDS::toMicros);
        System.err.println(snapshot);
        System.err.printf("queued requests: %d%n", requests.get());

        executor.shutdown();
    }

    private class PushingHandler extends TestHandler {
        @Override
        public void handle(String target, org.eclipse.jetty.server.Request jettyRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            if (target.equals("/")) {
                for (Resource resource : getResource().getResources()) {
                    jettyRequest.getPushBuilder()
                            .path(resource.getPath())
                            .setHeader("JLG-Response-Length", Integer.toString(resource.getResponseLength()))
                            .push();
                }
            }
            super.handle(target, jettyRequest, request, response);
        }
    }
}
