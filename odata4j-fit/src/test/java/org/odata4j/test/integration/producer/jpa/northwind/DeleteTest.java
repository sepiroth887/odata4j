package org.odata4j.test.integration.producer.jpa.northwind;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.core.OEntity;
import org.odata4j.core.OPredicates;

public class DeleteTest extends NorthwindJpaProducerTest {

  public DeleteTest(RuntimeFacadeType type) {
    super(type);
  }

  @Before
  public void setUpClass() throws Exception {
    super.setUp(20);
  }

  @Test
  public void tunneledDeleteEntity() {
    ODataConsumer consumer = this.rtFacade.createODataConsumer(endpointUri, null, "PUT");

    deleteEntityAndTest(consumer, "QUEEN");
  }

  @Test
  public void deleteEntity() {
    ODataConsumer consumer = this.rtFacade.createODataConsumer(endpointUri, null, null);

    deleteEntityAndTest(consumer, "ALFKI");
  }

  protected void deleteEntityAndTest(ODataConsumer consumer, String customerID) {

    OEntity customer = consumer.getEntity("Customers", customerID).execute();
    Assert.assertNotNull(customer);
    Assert.assertEquals(customerID, customer.getEntityKey().asSingleValue());

    Assert.assertNotNull(consumer.getEntities("Customers").execute().firstOrNull(OPredicates.entityPropertyValueEquals("CustomerID", customerID)));

    consumer.deleteEntity("Customers", customer.getEntityKey()).execute();

    Assert.assertNull(consumer.getEntities("Customers").execute().firstOrNull(OPredicates.entityPropertyValueEquals("CustomerID", customerID)));

  }

}
