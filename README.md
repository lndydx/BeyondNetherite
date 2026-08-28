# Beyond Netherite

Ever felt like the game ends too soon after getting Netherite? This mod pushes the progression further with Obsidian gear, buildable golems, hidden temples, and a boss that mirrors your own dark side.

**Minecraft Java Edition:** 26.2 · **Loader:** Fabric 0.19.3+ · **Fabric API:** 0.155.2+26.2

---

## Features

### Materials & Items
- **Obsidian Shard** — basic crafting component, obtained by crafting Obsidian.
- **Obsidian Alloy** — core material of the Obsidian tier, made from Obsidian + Netherite Scrap. Can also drop from Shade (5% chance).
- **Dense Obsidian** — a block used for crafting, decoration, and summoning rituals. Can be broken back down into 9 Obsidian.
- **Obsidian Arrow** — arrow with higher damage and a piercing effect.

### Equipment & Armor
- **Full Obsidian Tools set** — sword, axe, pickaxe, shovel, hoe, and spear.
- **Full Obsidian Armor set** — helmet, chestplate, leggings, boots.
- **Winged Chestplate** — a special variant of the Netherite and Obsidian chestplate with built-in elytra flight.
- All armor/tools are upgraded from the Netherite tier using an **Obsidian Smithing Template** + **Obsidian Alloy** at a Smithing Table.

### Structures & Entities
- **Temple of Shade** — a new structure serving as an arena to battle The Shade.
- **The Shade** — a mini-boss that guards the temple.
- **Obsidian Golem** — an upgraded version of the Iron Golem.

### How to Get the Obsidian Smithing Template
The template can be obtained from 2 sources:
- **Nether Fortress** chest — 15% chance
- **Bastion Remnant** chest — 20% chance

Once you have one, it can be duplicated using the recipe below.

<img width="484" height="271" alt="Template duplication recipe" src="https://github.com/user-attachments/assets/9e5f3965-bc8d-4c62-b0e1-f174d796b11e" />

---

## Crafting Recipes

### Obsidian Shard
**Shape:** shapeless · **Yield:** 4 shards

<img width="479" height="268" alt="Obsidian Shard recipe" src="https://github.com/user-attachments/assets/143c3495-9623-43ac-bd00-b11271d38910" />

### Obsidian Alloy
**Shape:** shaped 3x3 · **Yield:** 1 alloy
**Recipe:** 5 Netherite Scraps + 4 Obsidian

<img width="480" height="268" alt="Obsidian Alloy recipe" src="https://github.com/user-attachments/assets/408e7f08-12e2-4042-ae21-bbd637cfbdac" />

### Dense Obsidian
**Shape:** shaped 3x3 · **Yield:** 1 block
**Recipe:** 9 Obsidian

<img width="482" height="269" alt="Dense Obsidian recipe" src="https://github.com/user-attachments/assets/171ed7a5-b8ea-4c27-8e9c-8541c281bc40" />

### Dense Obsidian → Obsidian (reverse)
**Shape:** shapeless · **Yield:** 9 Obsidian

### Obsidian Arrow
**Shape:** shaped vertical · **Yield:** 2 arrows
**Recipe:** 1 Obsidian Shard + 1 Stick + 1 Feather

<img width="479" height="267" alt="Obsidian Arrow recipe" src="https://github.com/user-attachments/assets/a68ff124-038d-4d4f-be0e-452f992b481a" />

### Winged Chestplate
**Shape:** shaped vertical · **Yield:** 1 winged chestplate
**Recipe:** Netherite Ingot / Obsidian Alloy + Elytra + Netherite / Obsidian Chestplate

<img width="479" height="269" alt="Winged Chestplate recipe" src="https://github.com/user-attachments/assets/24f4069f-b367-4247-babb-15f25f79f239" />

### Armor & Tools Upgrade
**Recipe:** Netherite Tools / Armor + 

<img width="451" height="252" alt="Upgrade Armor and Toosl" src="https://github.com/user-attachments/assets/1836b525-1107-48ce-95fe-edb89b22d5a2" />

---

## Summoning Rituals

### Obsidian Golem
Arrange 4 Dense Obsidian into a T-shape (like an Iron Golem), then place a Carved Pumpkin or Jack o'Lantern on top.

```
       [Pumpkin]
[Dense] [Dense] [Dense]
        [Dense]
```

<img width="1919" height="1079" alt="Obsidian Golem summoning ritual" src="https://github.com/user-attachments/assets/f758ec5d-94fb-42d7-b533-4825030c8875" />

### The Shade
Stack vertically: 2 Dense Obsidian + 1 Wither Skeleton Skull (on top).

```
[Wither Skeleton Skull]
       [Dense]
       [Dense]
```

<img width="1919" height="1079" alt="Shade summoning ritual" src="https://github.com/user-attachments/assets/24f280e8-2dd2-4659-b8dd-af83e5985e2d" />

---

## Mini-Boss: The Shade

| Stat | Value |
|---|---|
| Health | 150 |
| Attack | 12 + enchantments (Sharpness III, Fire Aspect I) |
| Armor Toughness | 3.0 |
| Movement Speed | 0.27 (auto-sprints when within 12 blocks) |
| Follow Range | 50 blocks |
| XP Reward | 50 |
| Drop | Obsidian Alloy (5% chance) |

### Combat Behaviour
- **Lunge Attack** — leaps from 4–8 blocks away, 1.55x damage on critical hits (while airborne).
- **Flanking** — detects when the player is blocking with a shield and flanks to the side.
- **Strafing** — moves in a zigzag while chasing (random 5% chance, lasts 1–2 seconds).
- **Regeneration** — auto-regenerates when HP < 35% (Regeneration IV, 10 seconds, 7 second cooldown).

---

## Commands

```
/locate structure beyond-netherite:temple_of_shade
```

Finds the location of the nearest Temple of Shade.

---

## Installation

1. Install Fabric Loader 0.19.3+ for Minecraft 26.2.
2. Install Fabric API 0.155.2+26.2.
3. Put `beyond-netherite.jar` into your `mods` folder.

---

## License

MIT — see [LICENSE](LICENSE).
