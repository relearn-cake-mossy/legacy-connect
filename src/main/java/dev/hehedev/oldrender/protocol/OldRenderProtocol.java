package dev.hehedev.oldrender.protocol;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ServerboundPackets1_16_2;

public final class OldRenderProtocol extends AbstractProtocol<
    ClientboundPackets1_16_2,
    ClientboundPackets1_16_2,
    ServerboundPackets1_16_2,
    ServerboundPackets1_16_2
> {
    public static final OldRenderProtocol INSTANCE = new OldRenderProtocol();

    private OldRenderProtocol() {
        super(
            ClientboundPackets1_16_2.class,
            ClientboundPackets1_16_2.class,
            ServerboundPackets1_16_2.class,
            ServerboundPackets1_16_2.class
        );
    }
}
