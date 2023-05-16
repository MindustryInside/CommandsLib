package inside;

import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.CommandHandler;
import mindustry.game.Team;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.world.Tile;

public class CommandManager {

     private final CommandHandler handler;

     public CommandManager(CommandHandler handler) {
          this.handler = handler;
     }

     public CommandBuilder register(String name) {
          return null;
     }

     public static class CommandBuilder {
          String name;
          String description;

          ObjectMap<Param<?>, String> params;

          Cons<Seq<Object>> runnable;

          public CommandBuilder(String name) {
               this.name = name;
          }

          public CommandBuilder description(String description){
               this.description = description;
               return this;
          }

          public CommandBuilder param(String name, Param<?> param){
               this.params.put(param,name);
               return this;
          }

          public CommandBuilder handleServer(Cons<Seq<Object>> params){
               this.runnable = params;
               return this;
          }

          private void execute(String[] arr){
               Seq<Object> objects = new Seq<>();
               int i=0;
               for (Param<?> key : params.keys()) {
                    try {
                         objects.add(key.parse(arr[i]));
                    }catch (ArrayIndexOutOfBoundsException ex){
                         objects.add(new Object());
                    }

               }
               runnable.get(objects);
          }



          public interface CommandRunner<T>{
               void accept(String[] args, T parameter);
          }



          public interface Param<T> {
               default T parse(String arg) { return null; }
          }

          public static class PlayerParam implements Param<Player> {
               @Override
               public Player parse(String arg) {
                    return Param.super.parse(arg);
               }
          }

          public static class StringParam implements Param<String> {

          }

          public static class IntParam implements Param<Integer> {

          }

          public static class TeamParam implements Param<Team>{

          }

          public static class UnitParam implements Param<Unit>{

          }

          public static class TileParam implements Param<Tile>{

          }

          public static class BlockParam implements Param<Tile>{

          }

     }
}
