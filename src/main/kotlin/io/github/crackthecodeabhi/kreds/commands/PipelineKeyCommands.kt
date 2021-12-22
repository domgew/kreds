package io.github.crackthecodeabhi.kreds.commands

import io.github.crackthecodeabhi.kreds.args.*
import io.github.crackthecodeabhi.kreds.pipeline.QueuedCommand
import io.github.crackthecodeabhi.kreds.pipeline.Response

interface PipelineKeyCommands{
    /**
     * @see [KeyCommands.del]
     */
    suspend fun del(vararg keys: String): Response<Long>

    /**
     * @see [KeyCommands.copy]
     */
    suspend fun copy(source: String, destination: String, destinationDb: String? = null, replace: Boolean? = null): Response<Long>

    /**
     * @see [KeyCommands.dump]
     */
    suspend fun dump(key: String): Response<String?>

    /**
     * @see [KeyCommands.exists]
     */
    suspend fun exists(vararg keys: String): Response<Long>

    /**
     * @see [KeyCommands.expire]
     */
    suspend fun expire(key: String, seconds: ULong, expireOption: ExpireOption? = null): Response<Long>

    /**
     * @see [KeyCommands.expireAt]
     */
    suspend fun expireAt(key: String, timestamp: ULong, expireOption: ExpireOption? = null): Response<Long>

    /**
     * @see [KeyCommands.expireTime]
     */
    suspend fun expireTime(key: String): Response<Long>

    /**
     * @see [KeyCommands.keys]
     */
    suspend fun keys(pattern: String): Response<List<String>>

    /**
     * @see [KeyCommands.move]
     */
    suspend fun move(key: String, db: String): Response<Long>

    /**
     * @see [KeyCommands.persist]
     */
    suspend fun persist(key: String): Response<Long>

    /**
     * @see [KeyCommands.pexpire]
     */
    suspend fun pexpire(key: String, milliseconds: ULong,expireOption: PExpireOption? = null): Response<Long>

    /**
     * @see [KeyCommands.pexpireat]
     */
    suspend fun pexpireat(key:String, millisecondsTimestamp: ULong, expireOption: PExpireOption? = null /* = org.kreds.ExpireOption? */): Response<Long>

    /**
     * @see [KeyCommands.pexpiretime]
     */
    suspend fun pexpiretime(key: String): Response<Long>

    /**
     * @see [KeyCommands.pttl]
     */
    suspend fun pttl(key: String): Response<Long>

    /**
     * @see [KeyCommands.randomKey]
     */
    suspend fun randomKey(): Response<String?>

    /**
     * @see [KeyCommands.rename]
     */
    suspend fun rename(key: String, newKey: String): Response<String>

    /**
     * @see [KeyCommands.renamenx]
     */
    suspend fun renamenx(key: String, newKey: String): Response<Long>

    /**
     * @see [KeyCommands.touch]
     */
    suspend fun touch(vararg keys: String): Response<Long>

    /**
     * @see [KeyCommands.ttl]
     */
    suspend fun ttl(key: String): Response<Long>

    /**
     * @see [KeyCommands.type]
     */
    suspend fun type(key: String): Response<String>

    /**
     * @see [KeyCommands.unlink]
     */
    suspend fun unlink(vararg keys: String): Response<Long>

    /**
     * @see [KeyCommands.scan]
     */
    suspend fun scan(cursor: Long, matchPattern: String? = null, count: Long? = null, type: String? = null): Response<ScanResult>
}

interface PipelineKeyCommandExecutor: QueuedCommand, PipelineKeyCommands, BaseKeyCommands {

    override suspend fun del(vararg keys: String): Response<Long>  = add(_del(*keys))

    override suspend fun copy(source: String, destination: String, destinationDb: String?, replace: Boolean?): Response<Long> =
        add(_copy(source, destination, destinationDb, replace))

    override suspend fun dump(key: String): Response<String?> = add(_dump(key))

    override suspend fun exists(vararg keys: String): Response<Long> = add(_exists(*keys))

    override suspend fun expire(key: String, seconds: ULong, expireOption: ExpireOption?): Response<Long> =
        add(_expire(key, seconds, expireOption))

    override suspend fun expireAt(key: String, timestamp: ULong, expireOption: ExpireOption?): Response<Long> =
        add(_expireAt(key, timestamp, expireOption))

    override suspend fun expireTime(key: String): Response<Long> =
        add(_expireTime(key))

    override suspend fun keys(pattern: String): Response<List<String>> =
        add(_keys(pattern))

    override suspend fun move(key: String, db: String): Response<Long> =
        add(_move(key, db))

    override suspend fun persist(key: String): Response<Long> =
        add(_persist(key))

    override suspend fun pexpire(key: String, milliseconds: ULong, expireOption: PExpireOption?): Response<Long> =
        add(_pexpire(key, milliseconds, expireOption))

    override suspend fun pexpireat(key: String, millisecondsTimestamp: ULong, expireOption: PExpireOption?): Response<Long> =
        add(_pexpireat(key, millisecondsTimestamp, expireOption))

    override suspend fun pexpiretime(key: String): Response<Long> =
        add(_pexpiretime(key))

    override suspend fun pttl(key: String): Response<Long> =
        add(_pttl(key))

    override suspend fun randomKey(): Response<String?> =
        add(_randomKey())

    override suspend fun rename(key: String, newKey: String): Response<String> =
        add(_rename(key, newKey))

    override suspend fun renamenx(key: String, newKey: String): Response<Long> =
        add(_renamenx(key, newKey))

    override suspend fun touch(vararg keys: String): Response<Long> =
        add(_touch(*keys))

    override suspend fun ttl(key: String): Response<Long> =
        add(_ttl(key))

    override suspend fun type(key: String): Response<String> =
        add(_type(key))

    override suspend fun unlink(vararg keys: String): Response<Long> =
        add(_unlink(*keys))

    override suspend fun scan(cursor: Long, matchPattern: String?, count: Long?, type: String?): Response<ScanResult> =
        add(_scan(cursor, matchPattern, count, type))
}