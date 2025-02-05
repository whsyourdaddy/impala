// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.impala.analysis;

import org.apache.impala.catalog.FeIcebergTable;
import org.apache.impala.common.Pair;
import org.apache.impala.planner.DataSink;
import org.apache.impala.planner.TableSink;
import org.apache.impala.thrift.TSortingOrder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class IcebergDeleteImpl extends IcebergModifyImpl {
  public IcebergDeleteImpl(ModifyStmt modifyStmt) {
    super(modifyStmt);
  }

  @Override
  public DataSink createDataSink() {
    Preconditions.checkState(modifyStmt_.table_ instanceof FeIcebergTable);
    TableSink tableSink = TableSink.create(modifyStmt_.table_, TableSink.Op.DELETE,
        partitionKeyExprs_, resultExprs_, getReferencedColumns(), false, false,
        new Pair<>(ImmutableList.<Integer>of(), TSortingOrder.LEXICAL), -1, null,
        modifyStmt_.maxTableSinks_);
    Preconditions.checkState(!getReferencedColumns().isEmpty());
    return tableSink;
  }
}