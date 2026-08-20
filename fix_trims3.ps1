$base = 'D:\BeyondNetherite\src\main\resources'
$materials = @('quartz','iron','netherite','redstone','copper','gold','emerald','diamond','lapis','amethyst','resin')
$pieces = @( @('obsidian_helmet','helmet'), @('obsidian_chestplate','chestplate'), @('obsidian_leggings','leggings'), @('obsidian_boots','boots') )

foreach ($piece in $pieces) {
  $name = $piece[0]; $slot = $piece[1]
  $cases = @()
  foreach ($m in $materials) {
    $cases += '{"when":"minecraft:' + $m + '","model":{"type":"minecraft:model","model":"beyond-netherite:item/' + $name + '_' + $m + '_trim"}}'
  }
  # fallback INLINE (generated + layer0), gak nunjuk model lama lagi
  $itemsJson = '{"model":{"type":"minecraft:select","property":"minecraft:trim_material","cases":[' + ($cases -join ',') + '],"fallback":{"type":"minecraft:model","model":"minecraft:item/generated","textures":{"layer0":"beyond-netherite:item/' + $name + '"}}}}'
  [System.IO.File]::WriteAllText("$base\assets\beyond-netherite\items\$name.json", $itemsJson)

  # hapus model base lama biar gak konflik
  $old = "$base\assets\beyond-netherite\models\item\$name.json"
  if (Test-Path $old) { Remove-Item $old; Write-Host "HAPUS $name.json lama" }
}
Write-Host 'SELESAI. F3+T (kalau icon belum ganti, restart).'