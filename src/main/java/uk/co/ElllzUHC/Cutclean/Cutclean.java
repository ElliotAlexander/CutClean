package uk.co.ElllzUHC.Cutclean;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.ElllzUHC.Scenarios.Scenario;
import uk.co.ElllzUHC.Scenarios.ScenarioInterface;

import java.util.Random;

/**
 * Created by Elliot on 29/11/2014.
 */
public class Cutclean extends JavaPlugin implements ScenarioInterface,Listener {


    private boolean state;
    private String scenarioName;
    private String scenarioDescription;


    public void onEnable(){
        state = false;
        scenarioName = "CutClean";
        scenarioDescription = "Ores drop cooked, Food drops smelted.";

        Scenario manager = (Scenario) Bukkit.getPluginManager().getPlugin("ScenarioManager");
        manager.registerScenario(this);



    }


    public void setScenarioState(Boolean aBoolean) {
        state = aBoolean;
    }

    public boolean getScenarioState() {
        return state;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public String getScenarioDescription() {
        return scenarioDescription;
    }


    /**
     * Thanks to /u/Sitris for this source, which I wrote into my example.
     * @param event
     */

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        Bukkit.getLogger().info("FOUND A BLOCK BREAK!");
        if (!state) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }
        Block b = event.getBlock();
        World w = b.getWorld();
        Location l = b.getLocation();

        if (b.getType() == Material.IRON_ORE) {
            event.setCancelled(true);
            b.setType(Material.AIR);
            w.dropItemNaturally(l, new ItemStack(Material.IRON_INGOT));
            ExperienceOrb orb = w.spawn(l, ExperienceOrb.class);
            orb.setExperience(1);
        } else if (b.getType() == Material.GOLD_ORE) {
            event.setCancelled(true);
            b.setType(Material.AIR);
            w.dropItemNaturally(l, new ItemStack(Material.GOLD_INGOT));
            ExperienceOrb orb = w.spawn(l, ExperienceOrb.class);
            orb.setExperience(1);
        }

    }



    // All credit to /u/Sitris for this.
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!state) {
            return;
        }

        if (event.getEntity() instanceof Player) {
            return;
        }

        if (event.getEntity() instanceof Cow) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.COOKED_BEEF, 3));
            event.getDrops().add(new ItemStack(Material.LEATHER, 1));
        } else if (event.getEntity() instanceof Pig) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.GRILLED_PORK, 3));
        } else if (event.getEntity() instanceof Chicken) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 3));
            event.getDrops().add(new ItemStack(Material.FEATHER, 2));
        } else if (event.getEntity() instanceof Villager) {
            if (new Random().nextInt(99) < 50) {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.BOOK, 1));
            }
        } else if (event.getEntity() instanceof Horse) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.LEATHER, 2));
        } else if (event.getEntity() instanceof PigZombie) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, 1));
            event.getDrops().add(new ItemStack(Material.ROTTEN_FLESH, 1));
        } else if (event.getEntity() instanceof Spider || event.getEntity() instanceof CaveSpider) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.STRING, 2));
        } else if (event.getEntity() instanceof Zombie) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.ROTTEN_FLESH, 2));
        } else if (event.getEntity() instanceof Skeleton) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.ARROW, 2));
            event.getDrops().add(new ItemStack(Material.BONE, 1));
        } else if (event.getEntity() instanceof Creeper) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.SULPHUR, 2));
        }
    }



}
