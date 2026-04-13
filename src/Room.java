public class Room{
    private String roomType;
    private String description;
    private boolean visited;
    private boolean eventTriggered;
    private boolean cleared;
    private Item loot;
    private boolean lootGenerated;
    private static int count;
    public Room(){
        count++;
        if (count%5 == 0 ){
            this.roomType = "BOSS";
        }
        else{
            this.roomType = generateRandomType();
        }
        this.description = generateRandomDescription(roomType);
        this.visited = false;
        this.eventTriggered = false;
        this.cleared = false;
        this.loot = generateRandomLoot(type);
        this.lootGenerated = true;
    }
    private String generateRandomType() {
        int random = (int) (Math.random()*(3-1+1)+1);
        if (random == 1){
            return "SHOP";
        }
        else if (random == 2){
            return "MONSTER";
        }
        else{
            return "HEAL";
        }

    }

    public void enter() {
        visited = true;
    }

    public String describe() {
        return description;
    }

    public boolean isCleared() {
        return cleared;
    }

    public String getType() {
        return type;
    }

    public Item getLoot() {
        return loot;
    }

    private String generateRandomDescription(String type) {

        if (roomType.equals("MONSTER")) {
            String[] monsterDesc = {
                "You hear growling in the darkness.",
                "A foul stench fills the air.",
                "Something moves in the shadows."
            };
            return monsterDesc[(int)(Math.random() * monsterDesc.length)];
        }

        if (type.equals("HEAL")) {
            String[] healDesc = {
                "A warm light fills the room.",
                "You feel a soothing presence.",
                "A healing aura surrounds you."
            };
            return healDesc[(int)(Math.random() * healDesc.length)];
        }

        if (type.equals("SHOP")) {
            String[] shopDesc = {
                "A merchant greets you with a grin.",
                "You find a small traveling shop.",
                "A trader waves you over."
            };
            return shopDesc[(int)(Math.random() * shopDesc.length)];
        }

        if (type.equals("BOSS")) {
            String[] bossDesc = {
                "The air grows heavy… a powerful presence awaits.",
                "A massive shadow looms ahead.",
                "You feel overwhelming danger."
            };
            return bossDesc[(int)(Math.random() * bossDesc.length)];
        }

        return "Undefined room type.";
    }
}
