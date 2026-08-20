$base = 'D:\BeyondNetherite\src\main\resources'
$materials = @('quartz','iron','netherite','redstone','copper','gold','emerald','diamond','lapis','amethyst','resin')
$pieces = @(
  @('obsidian_helmet','helmet'),
  @('obsidian_chestplate','chestplate'),
  @('obsidian_leggings','leggings'),
  @('obsidian_boots','boots'),
  @('winged_obsidian_chestplate','chestplate'),
  @('winged_netherite_chestplate','chestplate')
)

foreach ($piece in $pieces) {
  $name = $piece[0]; $slot = $piece[1]

  # 1) HAPUS file _base.json sisa script lama
  $broken = "$base\assets\beyond-netherite\models\item\${name}_base.json"
  if (Test-Path $broken) { Remove-Item $broken }

  # 2) RESTORE base model dengan nama ASLI (persis vanilla pattern)
  $baseJson = '{"parent":"minecraft:item/generated","textures":{"layer0":"beyond-netherite:item/' + $name + '"}}'
  [System.IO.File]::WriteAllText("$base\assets\beyond-netherite\models\item\$name.json", $baseJson)

  # 3) Model trim per material
  $cases = @()
  foreach ($m in $materials) {
    $trimJson = '{"parent":"minecraft:item/generated","textures":{"layer0":"beyond-netherite:item/' + $name + '","layer1":"minecraft:trims/items/' + $slot + '_trim_' + $m + '"}}'
    [System.IO.File]::WriteAllText("$base\assets\beyond-netherite\models\item\${name}_${m}_trim.json", $trimJson)
    $cases += '{"when":"minecraft:' + $m + '","model":{"type":"minecraft:model","model":"beyond-netherite:item/' + $name + '_' + $m + '_trim"}}'
  }

  # 4) Items json - fallback nunjuk ke BASE MODEL (bukan inline), persis vanilla
  $itemsJson = '{"model":{"type":"minecraft:select","property":"minecraft:trim_material","cases":[' + ($cases -join ',') + '],"fallback":{"type":"minecraft:model","model":"beyond-netherite:item/' + $name + '"}}}'
  New-Item -ItemType Directory -Force -Path "$base\assets\beyond-netherite\items" | Out-Null
  [System.IO.File]::WriteAllText("$base\assets\beyond-netherite\items\$name.json", $itemsJson)
}

Write-Host 'SELESAI. RESTART GAME (tutup Minecraft total, run ulang dari IntelliJ).'