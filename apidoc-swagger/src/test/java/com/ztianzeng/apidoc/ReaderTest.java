package com.ztianzeng.apidoc;

import com.thoughtworks.qdox.model.JavaClass;
import com.ztianzeng.apidoc.model.ApiMethodDoc;
import com.ztianzeng.apidoc.models.OpenAPI;
import com.ztianzeng.apidoc.models.Operation;
import com.ztianzeng.apidoc.models.PathItem;
import com.ztianzeng.apidoc.models.Paths;
import com.ztianzeng.apidoc.res.BasicFieldsResource;
import com.ztianzeng.apidoc.swagger.Reader;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zhaotianzeng
 * @version V1.0
 * @date 2019-06-07 12:00
 */
public class ReaderTest {
    private static final String OPERATION_DESCRIPTION = "Operation Description";
    private static final String OPERATION_SUMMARY = "Operation Summary";
    private static final String PATH_1_REF = "/1";
    private static final int RESPONSES_NUMBER = 2;
    private static final int TAG_NUMBER = 2;
    private static final int SECURITY_SCHEMAS = 2;
    private static final int PARAMETER_NUMBER = 1;
    private static final int SECURITY_REQUIREMENT_NUMBER = 1;
    private static final int SCOPE_NUMBER = 2;
    private static final int PATHS_NUMBER = 1;
    private static final String EXAMPLE_TAG = "Example Tag";
    private static final String SECOND_TAG = "Second Tag";
    private static final String CALLBACK_POST_OPERATION_DESCRIPTION = "payload data will be sent";
    private static final String CALLBACK_GET_OPERATION_DESCRIPTION = "payload data will be received";
    private static final String RESPONSE_CODE_200 = "200";
    private static final String RESPONSE_DESCRIPTION = "voila!";
    private static final String EXTERNAL_DOCS_DESCRIPTION = "External documentation description";
    private static final String EXTERNAL_DOCS_URL = "http://url.com";
    private static final String PARAMETER_IN = "path";
    private static final String PARAMETER_NAME = "subscriptionId";
    private static final String PARAMETER_DESCRIPTION = "parameter description";
    private static final String CALLBACK_SUBSCRIPTION_ID = "subscription";
    private static final String CALLBACK_URL = "http://$request.query.url";
    private static final String SECURITY_KEY = "security_key";
    private static final String SCOPE_VALUE1 = "write:pets";
    private static final String SCOPE_VALUE2 = "read:pets";
    private static final String PATH_REF = "/";
    private static final String PATH_2_REF = "/path";
    private static final String SCHEMA_TYPE = "string";
    private static final String SCHEMA_FORMAT = "uuid";
    private static final String SCHEMA_DESCRIPTION = "the generated UUID";

    @Test
    public void testSimpleReadClass() {
        Reader reader = new Reader(new OpenAPI());
        OpenAPI openAPI = reader.read(BasicFieldsResource.class);
        Paths paths = openAPI.getPaths();
        assertEquals(paths.size(), 6);
        PathItem pathItem = paths.get(PATH_1_REF);
        assertNotNull(pathItem);
        assertNull(pathItem.getPost());
        Operation operation = pathItem.getGet();
        assertNotNull(operation);
        assertEquals(OPERATION_SUMMARY, operation.getSummary());
        assertEquals(OPERATION_DESCRIPTION, operation.getDescription());
    }

    @Test
    public void testScanMethods() {
        SourceBuilder sourceBuilder = new SourceBuilder();
        JavaClass classByName = sourceBuilder.getBuilder().getClassByName(SimpleMethods.class.getName());

        Reader reader = new Reader(new OpenAPI());

        List<ApiMethodDoc> apiMethodDocs = sourceBuilder.buildControllerMethod(classByName);
        for (final ApiMethodDoc method : apiMethodDocs) {
            Operation operation = reader.parseMethod(method);
            assertNotNull(operation);
        }
    }

    @Test
    public void testGetSummaryAndDescription() {
        Reader reader = new Reader(new OpenAPI());
        SourceBuilder sourceBuilder = new SourceBuilder();
        JavaClass classByName = sourceBuilder.getBuilder().getClassByName(BasicFieldsResource.class.getName());

        List<ApiMethodDoc> apiMethodDocs = sourceBuilder.buildControllerMethod(classByName);

        Operation operation = reader.parseMethod(apiMethodDocs.get(0));
        assertNotNull(operation);
        assertEquals(OPERATION_SUMMARY, operation.getSummary());
        assertEquals(OPERATION_DESCRIPTION, operation.getDescription());
    }

}