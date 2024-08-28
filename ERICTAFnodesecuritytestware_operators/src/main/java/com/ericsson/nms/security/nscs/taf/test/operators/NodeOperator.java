package com.ericsson.nms.security.nscs.taf.test.operators;

import java.util.Set;

public interface NodeOperator {



      boolean createNode(String nodeName,
                              String ipAddress);

      boolean checkNodeExists(final String nodeName);

      boolean deleteNode(final String nodeName);

      void deleteNodesBasedOnFilter(final String filter);

      Set<String> getAllNodes();

    }


