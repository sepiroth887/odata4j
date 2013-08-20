package org.odata4j.jersey.consumer.behaviors;

import org.odata4j.consumer.ODataClientRequest;
import org.odata4j.consumer.behaviors.OClientBehavior;

public class DataServiceVersionBehavior implements OClientBehavior {

    private static final String MAX_DATA_SERVICE_VERSION = "MaxDataServiceVersion";
    private final String dataVersion;

    private DataServiceVersionBehavior(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    @Override
    public ODataClientRequest transform(ODataClientRequest request) {
        if(request.getHeaders().containsKey(MAX_DATA_SERVICE_VERSION) &&
                !request.getHeaders().get(MAX_DATA_SERVICE_VERSION).equalsIgnoreCase(dataVersion)){
            throw new IllegalStateException("There is a conflicting MaxDataServiceVersion header already present: "
                    + request.getHeaders().get(MAX_DATA_SERVICE_VERSION));
        }
        return request.header(MAX_DATA_SERVICE_VERSION, dataVersion);
    }

    public static DataServiceVersionBehavior setVersion1(){
        return new DataServiceVersionBehavior("1.0");
    }

    public static DataServiceVersionBehavior setVersion2(){
        return new DataServiceVersionBehavior("2.0");
    }
}
