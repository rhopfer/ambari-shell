package com.sequenceiq.ambari.shell.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AmbariContextTest {

  private static final String HOST = "host";
  private static final String CLUSTER = "cluster";
  private AmbariContext ambariContext;

  @Before
  public void reset() {
    ambariContext = new AmbariContext();
  }

  @Test
  public void testGetPromptForNotConnected() {
    // WHEN
    String prompt = ambariContext.getPrompt();

    // THEN
    assertEquals("ambari-shell>", prompt);
  }

  @Test
  public void testGetPromptForConnected() {
    // GIVEN
    ambariContext.setCluster(CLUSTER);

    // WHEN
    String prompt = ambariContext.getPrompt();

    // THEN
    assertEquals(CLUSTER + " >", prompt);
  }

  @Test
  public void testGetPromptForSpecificHost() {
    // GIVEN
    ambariContext.setCluster(CLUSTER);
    ambariContext.setHost(HOST);

    // WHEN
    String prompt = ambariContext.getPrompt();

    // THEN
    assertEquals(CLUSTER + "/" + HOST + " >", prompt);
  }
}
