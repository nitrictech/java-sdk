/*-
 * #%L
 * Nitric Java SDK
 * %%
 * Copyright (C) 2021 Nitric Technologies Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package io.nitric.faas.http;

import io.nitric.faas.logger.JUtilLogger;
import io.nitric.faas.logger.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.nitric.faas.http.HttpRouter.Method.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides a HttpRouter unit test.
 */
public class HttpRouterTest {

    @Test
    public void test_toMethod() {
        assertSame(DELETE, valueOf("DELETE"));
        assertSame(GET, valueOf("GET"));
        assertSame(POST, valueOf("POST"));
        assertSame(PUT, valueOf("PUT"));
        assertSame(HEAD, valueOf("HEAD"));
        assertSame(OPTIONS, valueOf("OPTIONS"));
        assertSame(TRACE, valueOf("TRACE"));

        try {
            valueOf("POSTS");
            fail();
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test_addRoute() {
        // Test validation checks
        var router1 = new HttpRouter();

        try {
            router1.addRoute(null, "/", new TestMiddleware());
            fail();
        } catch (IllegalArgumentException iae) {
        }
        try {
            router1.addRoute(HttpRouter.Method.GET, null, new TestMiddleware());
            fail();
        } catch (IllegalArgumentException iae) {
        }
        try {
            router1.addRoute(HttpRouter.Method.GET, "", new TestMiddleware());
            fail();
        } catch (IllegalArgumentException iae) {
        }
        try {
            router1.addRoute(HttpRouter.Method.GET, "customers", new TestMiddleware());
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("provide path starting with '/' : customers", iae.getMessage());
        }
        try {
            router1.addRoute(HttpRouter.Method.GET, "/customers", (HttpMiddleware) null);
            fail();
        } catch (IllegalArgumentException iae) {
        }

        var get1 = new TestMiddleware();
        var get2 = new TestMiddleware();
        var get3 = new TestMiddleware();

        // Ensure cant add duplicate routes
        var router2 = new HttpRouter();

        router1.addRoute(HttpRouter.Method.GET, "/customer", get1);
        try {
            router1.addRoute(HttpRouter.Method.GET, "/customer", get2);
        } catch (IllegalArgumentException iae) {
            assertEquals("GET '/customer' route already configured with: TestMiddleware", iae.getMessage());
        }

        // Ensure routes are correctly ordered
        var router3 = new HttpRouter();

        router3.get(get1)
                .get("/customer", get2)
                .get("/customer/order", get3);

        var routes = router3.routeMap.get(HttpRouter.Method.GET);
        assertNotNull(routes);
        assertEquals(3, routes.size());

        assertEquals("/customer/order", routes.get(0).path);
        assertSame("/customer", routes.get(1).path);
        assertSame("/", routes.get(2).path);
    }

    @Test
    public void test_middleware_getRoute() {

        var delete1 = new TestMiddleware();
        var delete2 = new TestMiddleware();
        var delete3 = new TestMiddleware();
        var get1 = new TestMiddleware();
        var get2 = new TestMiddleware();
        var get3 = new TestMiddleware();
        var post1 = new TestMiddleware();
        var post2 = new TestMiddleware();
        var post3 = new TestMiddleware();
        var put1 = new TestMiddleware();
        var put2 = new TestMiddleware();
        var put3 = new TestMiddleware();
        var options1 = new TestMiddleware();
        var options2 = new TestMiddleware();
        var options3 = new TestMiddleware();

        var router = new HttpRouter()
                .delete("/customer", delete1)
                .delete("/customer/order", delete2)
                .delete(delete3)
                .get("/customer", get1)
                .get("/customer/order", get2)
                .get(get3)
                .post("/customer", post1)
                .post("/customer/order", post2)
                .post(post3)
                .put("/customer", put1)
                .put("/customer/order", put2)
                .put(put3)
                .options("/customer", options1)
                .options("/customer/order", options2)
                .options(options3);

        var ctxDelete1 = HttpContext.newBuilder().method("DELETE").path("/customer").build();
        assertSame(delete1, router.getRoute(ctxDelete1).middleware);

        var ctxDelete2 = HttpContext.newBuilder().method("DELETE").path("/customer/orders").build();
        assertSame(delete2, router.getRoute(ctxDelete2).middleware);

        var ctxDelete3 = HttpContext.newBuilder().method("DELETE").path("/").build();
        assertSame(delete3, router.getRoute(ctxDelete3).middleware);

        var ctxGet1 = HttpContext.newBuilder().method("GET").path("/customer").build();
        assertSame(get1, router.getRoute(ctxGet1).middleware);

        var ctxGet2 = HttpContext.newBuilder().method("GET").path("/customer/orders").build();
        assertSame(get2, router.getRoute(ctxGet2).middleware);

        var ctxGet3 = HttpContext.newBuilder().method("GET").path("/").build();
        assertSame(get3, router.getRoute(ctxGet3).middleware);

        var ctxPost1 = HttpContext.newBuilder().method("POST").path("/customer").build();
        assertSame(post1, router.getRoute(ctxPost1).middleware);

        var ctxPost2 = HttpContext.newBuilder().method("POST").path("/customer/orders").build();
        assertSame(post2, router.getRoute(ctxPost2).middleware);

        var ctxPost3 = HttpContext.newBuilder().method("POST").path("/").build();
        assertSame(post3, router.getRoute(ctxPost3).middleware);

        var ctxPut1 = HttpContext.newBuilder().method("PUT").path("/customer").build();
        assertSame(put1, router.getRoute(ctxPut1).middleware);

        var ctxPut2 = HttpContext.newBuilder().method("PUT").path("/customer/orders").build();
        assertSame(put2, router.getRoute(ctxPut2).middleware);

        var ctxPut3 = HttpContext.newBuilder().method("PUT").path("/").build();
        assertSame(put3, router.getRoute(ctxPut3).middleware);

        var ctxOptions1 = HttpContext.newBuilder().method("OPTIONS").path("/customer").build();
        assertSame(options1, router.getRoute(ctxOptions1).middleware);

        var ctxOptions2 = HttpContext.newBuilder().method("OPTIONS").path("/customer/orders").build();
        assertSame(options2, router.getRoute(ctxOptions2).middleware);

        var ctxOptions3 = HttpContext.newBuilder().method("OPTIONS").path("/").build();
        assertSame(options3, router.getRoute(ctxOptions3).middleware);

        var ctxNotFound = HttpContext.newBuilder().method("HEAD").path("/").build();
        assertSame(HttpRouter.NOT_FOUND_MIDDLEWARE, router.getRoute(ctxNotFound).middleware);

        // Test Not Found Middleware
        var notFound = new TestMiddleware();
        router.notFound(notFound);

        var ctxPatch = HttpContext.newBuilder().method("PATCH").path("/").build();
        assertSame(notFound, router.getRoute(ctxPatch).middleware);

        // Test Path Params
        var get4 = new TestMiddleware();
        router.get("/customer/order/{orderId}", get4);

        var ctxGet4 = HttpContext.newBuilder().method("GET").path("/customer/order/0123/?id=456").build();
        var route = router.getRoute(ctxGet4);

        assertEquals(GET, route.method);
        assertEquals("/customer/order/", route.path);
        assertEquals("[orderId]", route.pathParams.toString());
        assertSame(get4, route.middleware);

        assertEquals("{orderId=0123}", route.getPathParams(ctxGet4.getRequest().getPath()).toString());
    }

    @Test
    public void test_handler_getRoute() {

        var delete1 = new TestHandler();
        var delete2 = new TestHandler();
        var get1 = new TestHandler();
        var get2 = new TestHandler();
        var post1 = new TestHandler();
        var post2 = new TestHandler();
        var put1 = new TestHandler();
        var put2 = new TestHandler();
        var options1 = new TestHandler();
        var options2 = new TestHandler();

        var router = new HttpRouter()
                .delete("/customer", delete1)
                .delete(delete2)
                .get("/customer", get1)
                .get(get2)
                .post("/customer", post1)
                .post(post2)
                .put("/customer", put1)
                .put(put2)
                .options("/customer", options1)
                .options(options2);

        var ctxDelete1 = HttpContext.newBuilder().method("DELETE").path("/customer").build();
        assertSame(delete1, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxDelete1).middleware).getHandler());

        var ctxDelete2 = HttpContext.newBuilder().method("DELETE").path("/").build();
        assertSame(delete2, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxDelete2).middleware).getHandler());

        var ctxGet1 = HttpContext.newBuilder().method("GET").path("/customer").build();
        assertSame(get1, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxGet1).middleware).getHandler());

        var ctxGet2 = HttpContext.newBuilder().method("GET").path("/").build();
        assertSame(get2, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxGet2).middleware).getHandler());

        var ctxPost1 = HttpContext.newBuilder().method("POST").path("/customer").build();
        assertSame(post1, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxPost1).middleware).getHandler());

        var ctxPost2 = HttpContext.newBuilder().method("POST").path("/").build();
        assertSame(post2, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxPost2).middleware).getHandler());

        var ctxPut1 = HttpContext.newBuilder().method("PUT").path("/customer").build();
        assertSame(put1, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxPut1).middleware).getHandler());

        var ctxPut2 = HttpContext.newBuilder().method("PUT").path("/").build();
        assertSame(put2, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxPut2).middleware).getHandler());

        var ctxOptions1 = HttpContext.newBuilder().method("OPTIONS").path("/customer").build();
        assertSame(options1, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxOptions1).middleware).getHandler());

        var ctxOptions2 = HttpContext.newBuilder().method("OPTIONS").path("/").build();
        assertSame(options2, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxOptions2).middleware).getHandler());

        // Test Not Found Middleware
        var notFound = new TestHandler();
        router.notFound(notFound);

        var ctxNotFound = HttpContext.newBuilder().method("PATCH").path("/").build();
        assertSame(notFound, ((HttpMiddleware.HandlerAdapter) router.getRoute(ctxNotFound).middleware).getHandler());
    }

    @Test
    public void test_handle() {
        // Test Default Not Found
        var ctx1 = HttpContext.newBuilder().method("GET").path("/customer").build();

        var router = new HttpRouter();

        var ctx2 = router.handle(ctx1, HttpMiddleware.FINAL_MIDDLEWARE);
        assertEquals(404, ctx2.getResponse().getStatus());
        assertEquals("text/plain", ctx2.getResponse().getHeaders().get("Content-Type").get(0));
        assertEquals("Not Found", ctx2.getResponse().getDataAsText());

        // Test Set Not Found Handler
        var ctx3 = HttpContext.newBuilder().method("POST").path("/customer").build();

        router.notFound(new TestMiddleware(301, null, null));

        var ctx4 = router.handle(ctx3, HttpMiddleware.FINAL_MIDDLEWARE);
        assertEquals(301, ctx4.getResponse().getStatus());

        // Test Handler
        router.delete("/customer", new TestMiddleware("test 3"));

        var ctx5 = HttpContext.newBuilder().method("DELETE").path("/customer").build();
        var ctx6 = router.handle(ctx5, HttpMiddleware.FINAL_MIDDLEWARE);

        var res1 = ctx6.getResponse();
        assertEquals(200, res1.getStatus());
        assertEquals("text/plain", res1.getHeaders().get("Content-Type").get(0));
        assertEquals("test 3", res1.getDataAsText());

        // Test Path Params
        router.get("/customer/order/{orderId}", new TestMiddleware(418, "application/json", "teapot"));

        var ctx7 = HttpContext.newBuilder().method("GET")
                .path("/customer/order/0123/?id=456")
                .addQueryParam("id", "456")
                .build();
        var ctx8 = router.handle(ctx7, HttpMiddleware.FINAL_MIDDLEWARE);

        var res2 = ctx8.getResponse();
        assertEquals(418, res2.getStatus());
        assertEquals("application/json", res2.getHeaders().get("Content-Type").get(0));
        assertEquals("teapot", res2.getDataAsText());

        var req1 = ctx8.getRequest();
        assertEquals("/customer/order/0123/?id=456", req1.getPath());
        assertEquals(2, req1.getQueryParams().size());
        assertEquals("0123", req1.getQueryParam("orderId"));
        assertEquals("456", req1.getQueryParam("id"));

        // Test with Logger
        router.logger(new SysLogger());
        var ctx9 = router.handle(ctx7, HttpMiddleware.FINAL_MIDDLEWARE);

        var res3 = ctx9.getResponse();
        assertEquals(418, res3.getStatus());
        assertEquals("application/json", res3.getHeaders().get("Content-Type").get(0));
        assertEquals("teapot", res3.getDataAsText());

        var req2 = ctx9.getRequest();
        assertEquals("/customer/order/0123/?id=456", req2.getPath());
        assertEquals(2, req2.getQueryParams().size());
        assertEquals("0123", req2.getQueryParam("orderId"));
        assertEquals("456", req2.getQueryParam("id"));
    }

    @Test
    public void test_route() {
        var route = new HttpRouter.Route(GET, "/mycorp/service/orders/", List.of("customerId", "orderId"), null);

        var paramMap = route.getPathParams("/mycorp/service/orders/abc-123/def-456/");

        assertEquals("{customerId=abc-123, orderId=def-456}", paramMap.toString());

        assertEquals("Route[method=GET, path=/mycorp/service/orders/, pathParams=[customerId, orderId], middleware=null]",
                route.toString());
    }

    @Test
    public void test_logger() {
        var router = new HttpRouter();
        var logger = new JUtilLogger("test");
        router.logger(logger);

        assertSame(logger, router.logger);
    }

    // Inner Classes ----------------------------------------------------------

    public static class TestHandler implements HttpHandler {
        public HttpContext handle(HttpContext context) {
            return context;
        }
    }

    public static class TestMiddleware extends HttpMiddleware {

        Integer status;
        String contentType;
        String data;

        public TestMiddleware(Integer status, String contentType, String data) {
            this.status = status;
            this.contentType = contentType;
            this.data = data;
        }

        public TestMiddleware(String data) {
            this(null, "text/plain", data);
        }

        public TestMiddleware() {
        }

        public HttpContext handle(HttpContext context, HttpMiddleware next) {
            var response = context.getResponse();
            if (status != null) {
                response.status(status);
            }
            if (contentType != null) {
                response.contentType(contentType);
            }
            if (data != null) {
                response.text(data);
            }
            return context;
        }

        public String toString() {
            return getClass().getSimpleName();
        }
    }

    public static class SysLogger implements Logger {

        public void info(String format, Object... args) {
            System.out.printf(format + "\n", args);
        }

        public void error(String format, Object... args) {
            System.err.printf(format + "\n", args);
        }

        public void error(Throwable error, String format, Object... args) {
            System.err.printf(format + "\n", args);
            error.printStackTrace();
        }
    }
}
