Add-Type -AssemblyName System.Drawing
$base = 'D:\BeyondNetherite\src\main\resources\assets\beyond-netherite'
$tmp = "$env:TEMP\vn"
New-Item -ItemType Directory -Force -Path $tmp | Out-Null
$refs = @('26.2','master','main','latest')

function Get-V($path, $out) {
  foreach ($ref in $refs) {
    try {
      Invoke-WebRequest -Uri "https://raw.githubusercontent.com/PixiGeko/Minecraft-default-assets/$ref/assets/minecraft/$path" -OutFile $out -UseBasicParsing -TimeoutSec 20
      if ((Get-Item $out).Length -gt 20) { return $true }
    } catch { }
  }
  return $false
}

if (-not (Get-V 'textures/item/elytra.png' "$tmp\elytra.png")) { Write-Host 'GAGAL download elytra'; exit }
$ely = New-Object System.Drawing.Bitmap("$tmp\elytra.png")
function Sample($x, $y, $fr, $fg, $fb) {
  $p = $ely.GetPixel($x,$y)
  if ($p.A -gt 0) { return $p } else { return [System.Drawing.Color]::FromArgb(255,$fr,$fg,$fb) }
}
$mem  = Sample 4 8 116 110 118
$edge = Sample 1 5 62 58 66

# sayap kiri 16x16: tipis, ngalir ke bawah, ujung runcing
$rows = @(
  @(6,1,1), @(7,0,1), @(8,0,1), @(9,0,2), @(10,0,2),
  @(11,0,3), @(12,0,3), @(13,1,4), @(14,1,4), @(15,2,3)
)

function Merge($chestPath, $outPath) {
  $chest = New-Object System.Drawing.Bitmap($chestPath)
  $out = New-Object System.Drawing.Bitmap 16,16
  foreach ($r in $rows) {
    $y = $r[0]; $x0 = $r[1]; $x1 = $r[2]
    for ($x = $x0; $x -le $x1; $x++) {
      $c = if (($x -le $x0) -or ($y -ge 14)) { $edge } else { $mem }
      $out.SetPixel($x, $y, $c)
      $out.SetPixel(15-$x, $y, $c)
    }
  }
  # chestplate utuh + siluet simetris
  for ($y = 0; $y -lt 16; $y++) {
    for ($x = 0; $x -lt 16; $x++) {
      $p = $chest.GetPixel($x,$y)
      if ($p.A -gt 0) { $out.SetPixel($x,$y,$p) }
      else {
        $q = $chest.GetPixel(15-$x,$y)
        if ($q.A -gt 0) { $out.SetPixel($x,$y,$q) }
      }
    }
  }
  $out.Save($outPath, [System.Drawing.Imaging.ImageFormat]::Png)
  $out.Dispose(); $chest.Dispose()
  Write-Host "OK   $outPath"
}

Merge "$base\textures\item\obsidian_chestplate.png" "$base\textures\item\winged_obsidian_chestplate.png"
if (Get-V 'textures/item/netherite_chestplate.png' "$tmp\nc.png") {
  Merge "$tmp\nc.png" "$base\textures\item\winged_netherite_chestplate.png"
} else { Write-Host 'GAGAL download netherite chestplate' }
$ely.Dispose()
Write-Host 'SELESAI. F3+T.'