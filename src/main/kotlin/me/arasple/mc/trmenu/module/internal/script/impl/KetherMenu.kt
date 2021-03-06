package me.arasple.mc.trmenu.module.internal.script.impl

import io.izzel.taboolib.kotlin.kether.KetherParser
import io.izzel.taboolib.kotlin.kether.ScriptParser
import io.izzel.taboolib.kotlin.kether.common.api.ParsedAction
import io.izzel.taboolib.kotlin.kether.common.api.QuestContext
import io.izzel.taboolib.kotlin.kether.common.loader.types.ArgTypes
import me.arasple.mc.trmenu.api.TrMenuAPI
import me.arasple.mc.trmenu.api.event.MenuOpenEvent
import me.arasple.mc.trmenu.module.internal.script.kether.BaseAction
import java.util.concurrent.CompletableFuture
import kotlin.math.min

/**
 * @author Arasple
 * @date 2021/1/29 10:17
 */
class KetherMenu(val type: Type, val menu: ParsedAction<*>?) : BaseAction<Void>() {

    enum class Type {

        OPEN,

        PAGE,

        CLOSE

    }

    override fun process(context: QuestContext.Frame): CompletableFuture<Void> {
        val viewer = context.viewer()
        val session = viewer.session()

        when (type) {
            Type.CLOSE -> session?.close(closePacket = true, updateInventory = true)
            Type.PAGE -> context.newFrame(menu).run<Int>().thenApply {
                val menu = session?.menu ?: return@thenApply false
                val page = min(it.coerceAtLeast(0), menu.layout.getSize() - 1)

                menu.page(viewer, page)
            }
            Type.OPEN -> context.newFrame(menu).run<String>().thenApply {
                TrMenuAPI.getMenuById(it)?.open(context.viewer(), reason = MenuOpenEvent.Reason.CONSOLE)
            }
        }

        return completableFuture
    }

    companion object {

        @KetherParser(["menu"], namespace = "trmenu")
        fun parser() = ScriptParser.parser {
            val type = Type.valueOf(it.nextToken().toUpperCase())
            KetherMenu(
                type,
                if (type != Type.CLOSE) it.next(ArgTypes.ACTION) else null
            )
        }

    }

}