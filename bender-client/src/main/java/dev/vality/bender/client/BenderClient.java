package dev.vality.bender.client;

import dev.vality.bender.*;
import dev.vality.bender.client.configuration.BenderClientProperties;
import dev.vality.bender.client.exception.BenderException;
import dev.vality.msgpack.Nil;
import dev.vality.msgpack.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BenderClient {

    private final BenderSrv.Iface benderSrv;
    private final BenderClientProperties properties;

    public GenerationResult generateId(String externalID, GenerationSchema generationSchema, Value context) {
        log.info("GenerateId with externalID='{}', generationSchema='{}'", externalID, generationSchema);
        try {
            return benderSrv.generateID(externalID, generationSchema, context);
        } catch (TException ex) {
            throw new BenderException(
                    String.format("Can't generateId with externalID='%s', generationSchema='%s'", externalID,
                            generationSchema), ex);
        }
    }

    public GetInternalIDResult getInternalID(String externalID) {
        log.info("Get Internal ID with externalID='{}'", externalID);
        try {
            return benderSrv.getInternalID(externalID);
        } catch (TException ex) {
            throw new BenderException(String.format("Can't Get Internal ID with externalID='%s'", externalID), ex);
        }
    }

    // SequenceSchema
    public GenerationResult generateIdWithSequenceSchema(String externalID) {
        return generateIdWithSequenceSchema(externalID, properties.getNamespace(), null, Value.nl(new Nil()));
    }

    public GenerationResult generateIdWithSequenceSchema(String externalID, String namespace) {
        return generateIdWithSequenceSchema(externalID, namespace, null, Value.nl(new Nil()));
    }

    public GenerationResult generateIdWithSequenceSchema(String externalID, String namespace, Long sequenceMinimum) {
        return generateIdWithSequenceSchema(externalID, namespace, sequenceMinimum, Value.nl(new Nil()));
    }

    public GenerationResult generateIdWithSequenceSchema(String externalID, String namespace, Value context) {
        return generateIdWithSequenceSchema(externalID, namespace, null, context);
    }

    public GenerationResult generateIdWithSequenceSchema(String externalID, String namespace, Long sequenceMinimum,
                                                         Value context) {
        SequenceSchema sequenceSchema = new SequenceSchema();
        sequenceSchema.setSequenceId(namespace);
        if (sequenceMinimum != null) {
            sequenceSchema.setMinimum(sequenceMinimum);
        }
        GenerationSchema generationSchema = GenerationSchema.sequence(sequenceSchema);
        return generateId(externalID, generationSchema, context);
    }

    // ConstantSchema
    public GenerationResult generateIdWithConstantSchema(String externalID, String internalId) {
        return generateIdWithConstantSchema(externalID, internalId, Value.nl(new Nil()));
    }

    public GenerationResult generateIdWithConstantSchema(String externalID, String internalId, Value context) {
        ConstantSchema constantSchema = new ConstantSchema().setInternalId(internalId);
        GenerationSchema generationSchema = GenerationSchema.constant(constantSchema);
        return generateId(externalID, generationSchema, context);
    }

    // SnowflakeSchema
    public GenerationResult generateIdWithSnowflakeSchema(String externalID, Value context) {
        GenerationSchema generationSchema = GenerationSchema.snowflake(new SnowflakeSchema());
        return generateId(externalID, generationSchema, context);
    }

    public GenerationResult generateIdWithSnowflakeSchema(String externalID) {
        return generateIdWithSnowflakeSchema(externalID, Value.nl(new Nil()));
    }

}
