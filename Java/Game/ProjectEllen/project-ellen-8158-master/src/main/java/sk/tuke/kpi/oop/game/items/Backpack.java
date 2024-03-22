package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;

    private List<Collectible> content = new ArrayList<>();
    public Backpack(String name, int capacity){
        this.name=name;
        this.capacity=capacity;
    }

    public int getCapacity(){
        return capacity;
    }

    public String getName(){
        return name;
    }

    public List<Collectible> getContent(){
        return List.copyOf(content);
    }

    public int getSize(){
        return content.size();
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(actor!=null){
            if(capacity>content.size()){
                content.add(actor);
            }
            else{
                throw new IllegalStateException(name+" is full");
            }
        }
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        if(content!=null && actor != null && content.contains(actor)) {
            content.remove(actor);
        }
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return content.iterator();
    }

    @Override
    public @Nullable Collectible peek() {
        if(content!=null && !content.isEmpty()){
            return content.get(getSize()-1);
        }
        return null;
    }

    @Override
    public void shift() {
        if(content!=null && content.size()>1) {
            Collections.rotate(content, 1);
        }
    }
}
