package dev.hehedev.oldrender.mixin;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.platform.ViaDecodeHandler;
import com.viaversion.viaversion.platform.ViaEncodeHandler;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import dev.hehedev.oldrender.OldRenderMod;
import dev.hehedev.oldrender.protocol.OldRenderProtocol;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.network.ClientConnection$1")
public abstract class ClientConnectionChannelInitMixin {
    @Inject(method = "initChannel", at = @At("TAIL"), remap = false)
    private void oldrender$installTranslator(Channel channel, CallbackInfo callback) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT
            || !(channel instanceof SocketChannel)) {
            return;
        }

        if (channel.pipeline().get(ViaEncodeHandler.NAME) != null) {
            return;
        }

        UserConnection connection = new UserConnectionImpl(channel, true);
        connection.getProtocolInfo().setProtocolVersion(ProtocolVersion.v1_16_4);
        connection.getProtocolInfo().setServerProtocolVersion(OldRenderMod.targetVersion().get());
        new ProtocolPipelineImpl(connection).add(OldRenderProtocol.INSTANCE);

        channel.pipeline()
            .addBefore("encoder", ViaEncodeHandler.NAME, new ViaEncodeHandler(connection))
            .addBefore("decoder", ViaDecodeHandler.NAME, new ViaDecodeHandler(connection));
    }
}
