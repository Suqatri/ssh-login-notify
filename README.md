# ssh-login-notify
! This project is not intended to replace other security measures such as public keys! !

This project should make it possible to quickly identify SSH logins that may not have come from you, by sending a notification to your mobile phone. 
In the config you can set your region/city/IP. For all logins that do not match the data set in the config, a notification is sent to the mobile phone via webhook.

![image](https://user-images.githubusercontent.com/44299323/225137084-bc2f2547-a08f-4e81-8bd8-80a347753ace.png)

# Installation:
1. Install MacroDroid on your mobile phone
2. Import Macro: https://github.com/Suqatri/ssh-login-notify/blob/main/marco/SSH_Server_Login.macro
3. Log into your server and upload the jar (e.g. to the directory /home/ssh-notify)
4. Open .bashrc (e.g. nano ~/.bashrc)
5. Add following lines (! Important: Adjust the path of your jar !):
```bash
# 
# Send notification to mobile phone, if there is a weird login...
# 
if [ -n "$SSH_TTY" ]; then
   USERNAME=$(whoami)
   IP_ADDRESS=$(who -u am i | awk '{print $NF}')
   java -jar /home/ssh-notify/ssh-login-notify.jar "$USERNAME" "$IP_ADDRESS"
fi
``` 

6. Start the jar manuelly with java to create the default config.json
7. Edit the config.json
8. Now logins should be reported according to the config
