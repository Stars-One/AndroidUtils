# Android常用工具类
## Http
## Permission 
### 介绍 
Android6.0以上系统动态申请权限

### 使用说明：

#### 检测权限并申请权限
- `checkAndRequestPermission(Context context, String permission, int requestCode, PermissionRequestSuccessCallBack callBack)`
- `checkAndRequestPermission(Context context, String permission, int requestCode) `

#### 检测多个权限并申请权限
- `checkAndRequestMorePermissions(Context context, String[] permissions, int requestCode)`
- `checkAndRequestMorePermissions(Context context, String[] permissions, int requestCode) `

#### 跳转到权限设置界面
- `toAppSetting(Context context)`

#### 检测权限
- `checkPermission(Context context, String permission)`

	返回`true`或`false`
	
#### 检测多个权限
- `checkMorePermissions(Context context, String[] permissions)`

	返回未授权的String列表

#### 自定义权限
- 第一次申请权限：按照正常流程走；
- 如果用户第一次拒绝了权限申请，第二次申请时应向用户解释权限用途；
- 如果用户勾选了“不再询问”选项，应引导用户去设置页手动开启权限。

于是，引申出了复杂版的权限申请方法：
#####  自定义权限申请：
```
PermissionUtils.checkPermission(mContext, PERMISSION_CAMERA,
                new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                // 已授予权限
                toCamera();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                // 上一次申请权限被拒绝，可用于向用户说明权限原因，然后调用权限申请方法。
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                // 第一次申请权限或被禁止申请权限，建议直接调用申请权限方法。
            }
        });
```
#### 然后在onRequestPermissionsResult中：

```
PermissionUtils.onRequestPermissionResult(mContext, PERMISSION_CAMERA, grantResults, new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        toCamera();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        Toast.makeText(mContext, "我们需要"+Arrays.toString(permission)+"权限", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        Toast.makeText(mContext, "我们需要"+Arrays.toString(permission)+"权限", Toast.LENGTH_SHORT).show();
                        // 显示前往设置页的dialog
                        showToAppSettingDialog();
                    }
                });
```
## SMSReader 短信读取
```
getAllSMSBody(Activity activity)
```