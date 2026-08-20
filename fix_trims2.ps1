$base = 'D:\BeyondNetherite\src\main\resources'
Add-Type -AssemblyName System.Drawing

# 1) pastiin 18 trim wings transparan ada
$patterns = @('bolt','coast','dune','eye','flow','host','raiser','rib','sentry','shaper','silence','snout','spire','tide','vex','ward','wayfinder','wild')
$wingsDir = "$base\assets\minecraft\textures\trims\entity\wings"
New-Item -ItemType Directory -Force -Path $wingsDir | Out-Null
foreach ($p in $patterns) {
  $img = New-Object System.Drawing.Bitmap 64,32
  $img.Save("$wingsDir\$p.png", [System.Drawing.Imaging.ImageFormat]::Png)
  $img.Dispose()
}
Write-Host "OK   wings trims transparan"

# 2) override atlas armor_trims: vanilla + wings
$url = 'https://raw.githubusercontent.com/PixiGeko/Minecraft-default-assets/26.2/assets/minecraft/atlases/armor_trims.json'
$json = Invoke-RestMethod -Uri $url -TimeoutSec 30
$list = [System.Collections.ArrayList]::new(@($json.sources[0].textures))
foreach ($p in $patterns) { $list.Add("minecraft:trims/entity/wings/$p") | Out-Null }
$json.sources[0].textures = $list.ToArray()
$atlasDir = "$base\assets\minecraft\atlases"
New-Item -ItemType Directory -Force -Path $atlasDir | Out-Null
$json | ConvertTo-Json -Depth 10 | Set-Content "$atlasDir\armor_trims.json" -Encoding UTF8
Write-Host "OK   atlas override"
Write-Host 'SELESAI. Restart game.'