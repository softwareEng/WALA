/*******************************************************************************
 * Copyright (c) 2002 - 2006 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wala.util.graph;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.ibm.wala.util.collections.EmptyIterator;
import com.ibm.wala.util.collections.HashMapFactory;
import com.ibm.wala.util.collections.HashSetFactory;
import com.ibm.wala.util.collections.NonNullSingletonIterator;
import com.ibm.wala.util.debug.Assertions;
import com.ibm.wala.util.graph.traverse.DFSDiscoverTimeIterator;
import com.ibm.wala.util.graph.traverse.SlowDFSDiscoverTimeIterator;

/**
 * Calculate dominators using Langauer and Tarjan's fastest algorithm. TOPLAS
 * 1(1), July 1979. This implementation uses path compression and results in a
 * O(e * alpha(e,n)) complexity, where e is the number of edges in the CFG and n
 * is the number of nodes.
 * 
 * Sources: TOPLAS article, Muchnick book
 * 
 * @author Michael Hind
 * @author Julian Dolby
 */

public class NumberedDominators<T extends INodeWithNumber> 
    extends Dominators<T> 
{

  @SuppressWarnings("unchecked")
  public NumberedDominators(NumberedGraph<T> G, T root) 
      throws IllegalArgumentException 
  {
    super(G, root);

    this.infoMap = new Object[ G.getMaxNumber()+1 ];
    for(T n : G) {
      infoMap[ n.getGraphNodeId() ] = new DominatorInfo(n);
    }

    analyze();
  }

  public NumberedGraph<T> getGraph() {
    return (NumberedGraph)G;
  }

  /*
   * Look-aside table for DominatorInfo objects
   */
  private final Object[] infoMap;
  
  protected final DominatorInfo getInfo(T node) {
    return (DominatorInfo)infoMap[ node.getGraphNodeId() ];
  }

}