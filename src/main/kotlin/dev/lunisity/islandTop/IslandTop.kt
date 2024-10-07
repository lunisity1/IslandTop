package dev.lunisity.islandTop

import dev.lunisity.aurora.json.cache.impl.MapStorageCache
import dev.lunisity.aurora.json.manager.StorageManager
import dev.lunisity.aurora.json.manager.impl.StorageManagerImpl
import dev.lunisity.aurora.json.source.DataSource
import dev.lunisity.aurora.json.source.impl.FileDataSource
import dev.lunisity.borealis.component.Component
import dev.lunisity.borealis.json.JsonUtils
import dev.lunisity.islandTop.api.storage.creator.TrackedIslandCreator
import dev.lunisity.islandTop.api.storage.creator.TrackedUserCreator
import dev.lunisity.islandTop.api.storage.entity.TrackedIslandEntity
import dev.lunisity.islandTop.api.storage.entity.TrackedUserEntity
import dev.lunisity.islandTop.api.utils.LogUtil
import dev.lunisity.islandTop.commands.chat.CropTopCommand
import dev.lunisity.islandTop.commands.chat.FishTopCommand
import dev.lunisity.islandTop.commands.chat.LogTopCommand
import dev.lunisity.islandTop.commands.chat.OreTopCommand
import dev.lunisity.islandTop.commands.menu.CropTopMenuCommand
import dev.lunisity.islandTop.commands.menu.FishTopMenuCommand
import dev.lunisity.islandTop.commands.menu.IslandTopMenuCommand
import dev.lunisity.islandTop.commands.menu.LogTopMenuCommand
import dev.lunisity.islandTop.commands.menu.OreTopMenuCommand
import dev.lunisity.islandTop.listener.islands.CreationListener
import dev.lunisity.islandTop.listener.islands.DisbandListener
import dev.lunisity.islandTop.listener.islands.LeaveListener
import dev.lunisity.islandTop.listener.stats.FarmingListener
import dev.lunisity.islandTop.listener.stats.FishingListener
import dev.lunisity.islandTop.listener.stats.LoggingListener
import dev.lunisity.islandTop.listener.stats.MiningListener
import dev.lunisity.islandTop.manager.island.IslandStatManager
import dev.lunisity.islandTop.manager.MessageManager
import dev.lunisity.islandTop.manager.PlayerStatManager
import dev.lunisity.islandTop.manager.TopManager
import dev.lunisity.islandTop.manager.island.TrophyManager
import dev.lunisity.novaconfig.interfaces.Config
import dev.lunisity.novaconfig.manager.FileManager
import dev.lunisity.novamessages.messages.MessageCache
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class IslandTop : JavaPlugin(), Component<JavaPlugin> {

    companion object {
        val logger: Logger = Logger.getLogger("IslandTop")
        lateinit var instance: IslandTop
        lateinit var secretCodes: MutableList<String>
        lateinit var fileManager: FileManager
        lateinit var mainConfig: Config
        lateinit var menuConfig: Config
        lateinit var messagesConfig: Config
        lateinit var messageCache: MessageCache

        lateinit var userSource: DataSource<TrackedUserEntity>
        lateinit var islandSource: DataSource<TrackedIslandEntity>
        lateinit var userStorageManager: StorageManager<TrackedUserEntity>
        lateinit var islandStorageManager: StorageManager<TrackedIslandEntity>

        lateinit var playerManager: PlayerStatManager
        lateinit var islandManager: IslandStatManager
        lateinit var messageManager: MessageManager
        lateinit var topManager: TopManager
        lateinit var trophyManager: TrophyManager
    }

    override fun onEnable() {
        logger.warning("Loading IslandTop...")
        instance = this

        fileManager = FileManager(this)

        mainConfig = fileManager.load("config.yml")
        menuConfig = fileManager.load("menus.yml")

        secretCodes = mutableListOf("74130093345205078424")

        if (!secretCodes.contains(mainConfig.getString("Settings/Secret-Code"))) {
            logger.info("Secret code is incorrect!")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        if (!mainConfig.getBoolean("Settings/Enabled")) {
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        messagesConfig = fileManager.load("messages.yml")
        messageCache = MessageCache(messagesConfig, "Messages")

        val userFolder = fileManager.createStorageFolder("/users")
        val islandFolder = fileManager.createStorageFolder("/islands")

        userSource = FileDataSource(userFolder, TrackedUserEntity::class.java, JsonUtils.GSON)
        userStorageManager = StorageManagerImpl(userSource, MapStorageCache(), TrackedUserCreator())

        islandSource = FileDataSource(islandFolder, TrackedIslandEntity::class.java, JsonUtils.GSON)
        islandStorageManager = StorageManagerImpl(islandSource, MapStorageCache(), TrackedIslandCreator())

        playerManager = PlayerStatManager()
        islandManager = IslandStatManager()
        messageManager = MessageManager()
        topManager = TopManager()
        trophyManager = TrophyManager()

        Bukkit.getPluginManager().registerEvents(CreationListener(), this)
        Bukkit.getPluginManager().registerEvents(DisbandListener(), this)
        Bukkit.getPluginManager().registerEvents(LeaveListener(), this)
        Bukkit.getPluginManager().registerEvents(MiningListener(), this)
        Bukkit.getPluginManager().registerEvents(FarmingListener(), this)
        Bukkit.getPluginManager().registerEvents(FishingListener(), this)
        Bukkit.getPluginManager().registerEvents(LoggingListener(), this)

        LogUtil.warning("IslandTop loaded!")

        registerCommands()

        save()
    }

    fun registerCommands() {
        CropTopCommand().bind(this)
        FishTopCommand().bind(this)
        OreTopCommand().bind(this)
        LogTopCommand().bind(this)

        CropTopMenuCommand().bind(this)
        FishTopMenuCommand().bind(this)
        OreTopMenuCommand().bind(this)
        LogTopMenuCommand().bind(this)
        IslandTopMenuCommand().bind(this)
    }

    fun save() {
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            userStorageManager.save()
            islandStorageManager.save()
        }, 0, 120000L)
    }

    override fun onDisable() {
        logger.warning("Disabling IslandTop...")

        userStorageManager.save()
        islandStorageManager.save()
    }

    override fun getLoader(): IslandTop {
        return this
    }
}
