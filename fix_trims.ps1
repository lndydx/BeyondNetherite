$base = 'D:\BeyondNetherite\src\main\resources'
Add-Type -AssemblyName System.Drawing

$patterns = @('bolt','coast','dune','eye','flow','host','raiser','rib','sentry','shaper','silence','snout','spire','tide','vex','ward','wayfinder','wild')
$materials = @('quartz','iron','netherite','redstone','copper','gold','emerald','diamond','lapis','amethyst','resin')
$pieces = @( @('obsidian_helmet','helmet'), @('obsidian_chestplate','chestplate'), @('obsidian_leggings','leggings'), @('obsidian_boots','boots') )

# 1) trim wings TRANSPARAN (biar sayap gak magenta)
$wingsDir = "$base\assets\minecraft\textures\trims\entity\wings"
New-Item -ItemType Directory -Force -Path $wingsDir | Out-Null
foreach ($p in $patterns) {
  $img = New-Object System.Drawing.Bitmap 64,32
  $img.Save("$wingsDir\$p.png", [System.Drawing.Imaging.ImageFormat]::Png)
  $img.Dispose()
}
Write-Host "OK   18 trim wings transparan"

# 2) model trim per armor piece + items json (icon hotbar nunjukin trim)
foreach ($piece in $pieces) {
  $name = $piece[0]; $slot = $piece[1]
  $cases = @()
  foreach ($m in $materials) {
    $modelJson = '{"parent":"minecraft:item/generated","textures":{"layer0":"beyond-netherite:item/' + $name + '","layer1":"minecraft:trims/items/' + $slot + '_trim_' + $m + '"}}'
    $modelPath = "$base\assets\beyond-netherite\models\item\${name}_${m}_trim.json"
    [System.IO.File]::WriteAllText($modelPath, $modelJson)
    $cases += '{"when":"minecraft:' + $m + '","model":{"type":"minecraft:model","model":"beyond-netherite:item/' + $name + '_' + $m + '_trim"}}'
  }
  $itemsJson = '{"model":{"type":"minecraft:select","property":"minecraft:trim_material","cases":[' + ($cases -join ',') + '],"fallback":{"type":"minecraft:model","model":"beyond-netherite:item/' + $name + '"}}}'
  New-Item -ItemType Directory -Force -Path "$base\assets\beyond-netherite\items" | Out-Null
  [System.IO.File]::WriteAllText("$base\assets\beyond-netherite\items\$name.json", $itemsJson)
}
Write-Host "OK   items json + 44 model trim"
Write-Host 'SELESAI. F3+T (kalau masih magenta, restart).'