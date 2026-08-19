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

# sayap kiri: lebih lebar (kolom 1), lebih atas (6), bawah runcing
$rows = @(
  @(6,11,12), @(7,10,12), @(8,9,12), @(9,8,12), @(10,7,13),
  @(11,6,13), @(12,5,13), @(13,4,13), @(14,3,13), @(15,2,13),
  @(16,2,13), @(17,1,13), @(18,1,13), @(19,1,13), @(20,1,13),
  @(21,1,13), @(22,1,13), @(23,1,13), @(24,1,13), @(25,1,12),
  @(26,1,12), @(27,2,11), @(28,2,10), @(29,3,9), @(30,4,8),
  @(31,5,7)
)

function Merge($chestPath, $outPath) {
  $chest = New-Object System.Drawing.Bitmap($chestPath)
  $out = New-Object System.Drawing.Bitmap 32,32
  foreach ($r in $rows) {
    $y = $r[0]; $x0 = $r[1]; $x1 = $r[2]
    for ($x = $x0; $x -le $x1; $x++) {
      $c = if (($x -le $x0+1) -or ($x -ge $x1) -or ($y -ge 30)) { $edge } else { $mem }
      $out.SetPixel($x, $y, $c)
      $out.SetPixel(31-$x, $y, $c)
    }
  }
  for ($y = 0; $y -lt 32; $y++) {
    $sy = [Math]::Floor(($y+2)/2)
    if ($sy -gt 15) { continue }
    for ($x = 0; $x -lt 32; $x++) {
      $sx = [Math]::Floor($x/2); $mx = 15-$sx
      $p = $chest.GetPixel($sx,$sy)
      if ($p.A -gt 0) { $out.SetPixel($x,$y,$p) }
      else {
        $q = $chest.GetPixel($mx,$sy)
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
Write-Host 'SELESAI. Tutup tab preview, buka lagi.'