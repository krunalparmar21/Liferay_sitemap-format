package com.cemex.format;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SitemapFilterClass implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        XmlResponseWrapper capturingResponseWrapper = new XmlResponseWrapper((HttpServletResponse) response);

        chain.doFilter(request, capturingResponseWrapper);
        PrintWriter responseWriter = response.getWriter();

        if (response.getContentType() != null
                && response.getContentType().contains("text/xml")) {

            String content = capturingResponseWrapper.getCaptureAsString();

            String replaceConetnt = content.replaceFirst("xmlns:xhtml=\\\"http://www.w3.org/1999/xhtml",
                            "xmlns:xhtml=\"http://www.w3.org/TR/xhtml11/xhtml11_schema.html").
                    replace("xsi:schemaLocation=\"http://www.w3.org/1999/xhtml http://www.w3.org/2002/08/xhtml/xhtml1-strict.xsd"
                            ,"xsi:schemaLocation=\"http://www.w3.org/TR/xhtml11/xhtml11_schema.html http://www.w3.org/2002/08/xhtml/xhtml1-strict.xsd");

            responseWriter.write(replaceConetnt);

        }
    }

    @Override
    public void destroy() {

    }
}
