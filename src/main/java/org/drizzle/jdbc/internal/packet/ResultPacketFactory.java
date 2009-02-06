package org.drizzle.jdbc.internal.packet;

import org.drizzle.jdbc.internal.packet.buffer.ReadBuffer;

import java.io.InputStream;
import java.io.IOException;

/**
 * Creates result packets
 * only handles error, ok, eof and result set packets since field
 * and row packets require a previous result set packet
 * User: marcuse
 * Date: Jan 16, 2009
 * Time: 1:12:23 PM
 */
public class ResultPacketFactory {
    private final static byte ERROR=(byte)0xff;
    private final static byte OK=(byte)0x00;
    private final static byte EOF = (byte)0xfe;

    public static ResultPacket createResultPacket(InputStream reader) throws IOException {
        ReadBuffer readBuffer = new ReadBuffer(reader);
        switch(readBuffer.getByteAt(0)) {
            case ERROR:
                return new ErrorPacket(readBuffer);
            case OK:
                return new OKPacket(readBuffer);
            case EOF:
                return new EOFPacket(readBuffer);
            default:
                return new ResultSetPacket(readBuffer);
        }
    }
}