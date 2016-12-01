Record the details of your config in the table below.  If you have not used Github before, there are a number of steps : 

Edit file >> Click 'Propose Changes' >> Click 'Create Pull request'

Dont worry about making a mistake - your changes are reviewed before they are added into the master version.

See here for a guide on this process : https://github.com/philscanlon/Hackathon2016/blob/master/docs/How-to-add-your-router-details.md

### Core Routers  - DO NOT Change

**Router Name**|**Region Owner**|**Public ip Address**
:-----:|:-----:|:-----:
nov-core| | 
ohi-core| | 
cal-core| | 
org-core| | 
ire-core| | 
frk-core| | 
tky-core| | 
seo-core| | 
sgp-core| Phil Scanlon | 52.220.157.193
syd-core| | 
mum-core| | 
sao-core| | 

### Participant Routers

**Name**|**AWS Region**|**Router Name**|**Router IP**|**Connection 1**|**Connection 2**
:-----:|:-----:|:-----:|:-----:|:-----:|:-----:
Amit Gothecha|Asia Pacific (Mumbai)|mum-agothecha| |MUM-CORE| 
Arghya Sanyal|Asia Pacific (Mumbai)|mum-asanyal| |MUM-CORE| 
Clarence Ching|Asia Pacific (Seoul)|seo-cching| |SEO-CORE| 
Jet Huang|Asia Pacific (Seoul)|seo-jhuang| |SEO-CORE| 
Vincent Lam|Asia Pacific (Seoul)|seo-vlam| |SEO-CORE| 
Eunsuk Yoon|Asia Pacific (Seoul)|seo-eyoon| |SEO-CORE| 
Eric Asuncion|Asia Pacific (Singapore)|sgp-easuncion| |SGP-CORE| 
Steve Chan|Asia Pacific (Singapore)|sgp-schan| |SGP-CORE| 
Robert Hsieh|Asia Pacific (Singapore)|sgp-rhsieh| |SGP-CORE| 
Sumeet Koshal|Asia Pacific (Singapore)|sgp-skoshal| |SGP-CORE| 
Shrikanth Rajgopalan|Asia Pacific (Singapore)|sgp-srajgopalan| |SGP-CORE| 
Eric Sun|Asia Pacific (Singapore)|sgp-esun| |SGP-CORE| 
John Filo|Asia Pacific (Sydney)|syd-jfilo| |SYD-CORE| 
Naomoto Matsuki|Asia Pacific (Tokyo)|tky-nmatsuki| |TKY-CORE| 
Magali Boulet|EU (Frankfurt)|frk-mboulet| |FRK-CORE| 
Perry Krol|EU (Frankfurt)|frk-pkrol| |FRK-CORE| 
Tom Fairbairn|EU (Ireland)|ire-tfairbairn| |IRE-CORE| 
Mathew Hobbis|EU (Ireland)|ire-mhobbis| |IRE-CORE| 
Christian Holtfurth|EU (Ireland)|ire-choltfurth| |IRE-CORE| 
Benjamin Taieb|EU (Ireland)|ire-btaieb| |IRE-CORE| 
Paul Woodward|EU (Ireland)|ire-pwoodward| |IRE-CORE| 
David Wray|EU (Ireland)|ire-dwray| |IRE-CORE| 
Ken Barr|US East (N. Virginia)|nov-kbarr| |NOV-CORE-A| 
Floyd Davis|US East (N. Virginia)|nov-fdavis| |NOV-CORE-A| 
Dmitri Fedorov|US East (N. Virginia)|nov-dfedorov| |NOV-CORE-A| 
Luis Horna|US East (N. Virginia)|nov-lhorna| |NOV-CORE-A| 
Mark Spielman|US East (N. Virginia)|nov-mspielman| |NOV-CORE-A| 
Dishant Langayan|US East (N. Virginia)|nov-dlangayan| |NOV-CORE-A| 
Jim Lowe|US East (N. Virginia)|nov-jlowe| |NOV-CORE-A| 
Sanjeev Nagpal|US East (N. Virginia)|nov-snagpal| |NOV-CORE-B| 
Tom O'Leary|US East (N. Virginia)|nov-to'leary| |NOV-CORE-B| 
Wayne Osse|US East (N. Virginia)|nov-wosse| |NOV-CORE-B| 
Ken Overton|US East (N. Virginia)|nov-koverton| |NOV-CORE-B| 
David Pochopsky|US East (N. Virginia)|nov-dpochopsky| |NOV-CORE-B| 
Wayne Sang|US East (N. Virginia)|nov-wsang| |NOV-CORE-B| 
Rodrigo Abreu|US East (Ohio)|ohi-rabreu| |OHI-CORE| 
Peter Blinstrubas|US East (Ohio)|ohi-pblinstrubas| |OHI-CORE| 
Mahesh Kaleru|US East (Ohio)|ohi-mkaleru| |OHI-CORE| 
Heinz Schaffner|US East (Ohio)|ohi-hschaffner| |OHI-CORE| 
Ifoma Smart|US East (Ohio)|ohi-ismart| |OHI-CORE| 
Matthew Stobo|US East (Ohio)|ohi-mstobo| |OHI-CORE| 
Michael Hilmen|US West (N. California)|cal-mhilmen| |CAL-CORE| 
Manuel Moreno|South America (São Paulo)|sao-mmoreno| |SAO-CORE| 
Ramesh Natarajan|US West (N. California)|cal-rnatarajan| |CAL-CORE| 
Dale Baik|US West (Oregon)|org-dbaik| |ORG-CORE| 
Andrew Broome|US West (Oregon)|org-abroome| |ORG-CORE| 



| Name | Location  | Router_IP  |  RouterName |AWS Region | Notes  |
|------|-----------|------------|-------------|--------|----------|
| Aaron Lee | Hong Kong | 52.78.245.158 | hkgsolaa01 | AP NE (Seoul) | |
| David Wray | London | 52.31.220.231 | DavidHackathon   | EU (Ireland) | |
| Phil Scanlon | Singapore | 52.77.102.138  | hackathon-singapore-vmr | Asia Pacific (Singapore) | |
| Phil Scanlon | Singapore | 52.220.111.223 | pscanlon-vmr   | Asia Pacific (Singapore) | |
| Sumeet Koshal | Singapore | 52.220.165.194 | sumeet-vmr   | Asia Pacific (Singapore) | |
| Vidyadhar Kothekar | Sydney | 13.54.186.240  | Vidya-VMR-Sydney | Asia Pacific (Sydney) | |


# Link Costs:

<b>Note: should be symmetric!!</b>
How to enforce??

| Between | And | Cost |
|---------|-----|------|
| pscanlon-vmr | hkgsolaa01 | 30 |
| hkgsolaa01 | DavidHackathon | 100 |

# Config

Rather than just use the defaults, we could tune a few things... practice!

 - Enable compression on your neighbour links
   - Ensure port 55003 is exposed in AWS
 - Tune TCP params:
   - max-wnd = 1024 (1MB)
   - initial-cwd = 10
 - Tune the CSPF NAB queues if you really want
   - max-length = 40000
   - min-burst = 500



