/*
 * Copyright (C) 2014-2015 LinkedIn Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.
 */

package gobblin.source.extractor.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.RecordReader;


/**
 * An extension of {@link OldApiHadoopFileInputExtractor} for extracting {@link Writable} records.
 *
 * @author ziliu
 */
public class OldApiWritableFileExtractor extends OldApiHadoopFileInputExtractor<Object, Writable, Object, Writable> {

  public OldApiWritableFileExtractor(RecordReader<Object, Writable> recordReader, boolean readKeys) {
    super(recordReader, readKeys);
  }

  @Override
  public Object getSchema() throws IOException {
    return null;
  }
}
