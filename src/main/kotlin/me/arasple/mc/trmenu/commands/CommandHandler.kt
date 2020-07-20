package me.arasple.mc.trmenu.commands

import io.izzel.taboolib.module.command.base.BaseCommand
import io.izzel.taboolib.module.command.base.BaseMainCommand
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.command.base.SubCommand
import io.izzel.taboolib.module.command.base.display.DisplayBase
import io.izzel.taboolib.module.command.base.display.DisplayFlat
import me.arasple.mc.trmenu.commands.sub.CommandDebug
import me.arasple.mc.trmenu.commands.sub.CommandListMenu
import me.arasple.mc.trmenu.commands.sub.CommandOpenMenu
import me.arasple.mc.trmenu.commands.sub.CommandReload

/**
 * @author Arasple
 * @date 2020/5/30 14:11
 */
@BaseCommand(name = "trmenu", aliases = ["tmenu", "menu"], permission = "trmenu.use")
class CommandHandler : BaseMainCommand() {

    @SubCommand(permission = "trmenu.command.reload", description = "Reload menus")
    val reload: BaseSubCommand = CommandReload()

    @SubCommand(permission = "trmenu.command.open", description = "Open a menu for player")
    val open: BaseSubCommand = CommandOpenMenu()

    @SubCommand(permission = "trmenu.command.list", description = "List loaded menus")
    val list: BaseSubCommand = CommandListMenu()

    @SubCommand(permission = "trmenu.command.debug", description = "Enable debug mode for player or print debug info to console")
    val debug: BaseSubCommand = CommandDebug()

    override fun getDisplay(): DisplayBase = DisplayFlat()

}