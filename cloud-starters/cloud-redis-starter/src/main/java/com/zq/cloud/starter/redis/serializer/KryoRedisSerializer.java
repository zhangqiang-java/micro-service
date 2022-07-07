package com.zq.cloud.starter.redis.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.data.redis.serializer.RedisSerializer;

public class KryoRedisSerializer implements RedisSerializer<Object> {

    public static final int MAX_BUFFER_SIZE = -1;

    public static final int INITIAL_BUFFER_SIZE = 1024 * 2;

    private static final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            kryo.setRegistrationRequired(false);
            DefaultInstantiatorStrategy instantiatorStrategy = (DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy();
            instantiatorStrategy.setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };

    private static ThreadLocal<Output> outputThreadLocal = new ThreadLocal<Output>() {

        @Override
        protected Output initialValue() {
            return new Output(INITIAL_BUFFER_SIZE, MAX_BUFFER_SIZE);
        }
    };


    public static Kryo getKryoInstance() {
        return kryoLocal.get();
    }

    public static Output getOutputInstance() {
        return outputThreadLocal.get();
    }



    @Override
    public byte[] serialize(Object t) {
        Kryo kryo = getKryoInstance();
        Output output = getOutputInstance();
        try {
            kryo.writeClassAndObject(output, t);
            return output.toBytes();
        } finally {
            output.reset();
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Kryo kryo = getKryoInstance();
        Input input = new Input();
        input.setBuffer(bytes);
        return kryo.readClassAndObject(input);
    }

}
