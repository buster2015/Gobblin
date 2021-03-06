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

package gobblin.writer;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import gobblin.configuration.State;
import gobblin.util.AvroUtils;
import gobblin.util.WriterUtils;


/**
 * A {@link DataWriterBuilder} for building {@link DataWriter} that writes in Avro format.
 *
 * @author ynli
 */
@SuppressWarnings("unused")
public class AvroDataWriterBuilder extends PartitionAwareDataWriterBuilder<Schema, GenericRecord> {

  @Override public boolean validatePartitionSchema(Schema partitionSchema) {
    return true;
  }

  @Override
  public DataWriter<GenericRecord> build()
      throws IOException {
    Preconditions.checkNotNull(this.destination);
    Preconditions.checkArgument(!Strings.isNullOrEmpty(this.writerId));
    Preconditions.checkNotNull(this.schema);
    Preconditions.checkArgument(this.format == WriterOutputFormat.AVRO);

    switch (this.destination.getType()) {
      case HDFS:
        State properties = this.destination.getProperties();

        String fileName = WriterUtils
            .getWriterFileName(properties, this.branches, this.branch, this.writerId, this.format.getExtension());

        if(this.partition.isPresent()) {
          fileName = new Path(AvroUtils.serializeAsPath(this.partition.get(), true).toString(), fileName).toString();
        }

        return new AvroHdfsDataWriter(properties, fileName, this.schema, this.branches, this.branch);
      case KAFKA:
        return new AvroKafkaDataWriter();
      default:
        throw new RuntimeException("Unknown destination type: " + this.destination.getType());
    }
  }
}
