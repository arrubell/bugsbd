package org.unichain.common.logsfilter;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class EventPluginConfig {

  public static final String BLOCK_TRIGGER_NAME = "block";
  public static final String TRANSACTION_TRIGGER_NAME = "transaction";
  public static final String CONTRACTEVENT_TRIGGER_NAME = "contractevent";
  public static final String CONTRACTLOG_TRIGGER_NAME = "contractlog";

  @Getter
  @Setter
  private String pluginPath;

  @Getter
  @Setter
  private String serverAddress;

  @Getter
  @Setter
  private String dbConfig;

  @Getter
  @Setter
  private boolean useNativeQueue;

  @Getter
  @Setter
  private boolean enable;

  @Getter
  @Setter
  private int bindPort;

  @Getter
  @Setter
  private int sendQueueLength;


  @Getter
  @Setter
  private List<TriggerConfig> triggerConfigList;

  public EventPluginConfig() {
    pluginPath = "";
    serverAddress = "";
    dbConfig = "";
    useNativeQueue = false;
    enable = false;
    bindPort = 0;
    sendQueueLength = 0;
    triggerConfigList = new ArrayList<>();
  }
}
