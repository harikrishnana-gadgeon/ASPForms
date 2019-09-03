Copy-Item .\hari\* D:\test\

Import-Module WebAdministration;

  cd IIS:\AppPools\;

  $appPool = $null;
  $name = "Hello";

  #check if the app pool exists
  if (!(Test-Path $name -pathType container))
  {
    $appPool = New-Item $name;    
  }
  else
  {
    $appPool = Get-Item $name;    
  }
  
    
  $appPool.Enable32BitAppOnWin64 = $enable32BitAppOnWin64;

  $appPool | Set-Item; 

if (!(Test-Path $name -pathType container))
  {
    New-Website -Name $name -Port 8001 -IPAddress "*" -HostHeader $url -PhysicalPath "D:\test\" -ApplicationPool $name;
  }