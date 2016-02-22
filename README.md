# MPermissions

基于Annotation Processor的简单易用的处理Android M运行时权限的库。

部分代码来自[PermissionGen](https://github.com/hongyangAndroid/PermissionGen)，
主要是将其基于运行时注解的实现修改为Annotation Processor的方式，即编译时注解。

## 引入

Eclipse和Android Studio下载最近的jar文件导入到项目即可，[下载地址](permission-lib/build/libs/permission-lib.jar)
对于AS别忘了：

```
dependencies {
    compile files('libs/permission-lib.jar')
}
```

## 使用

* 申请权限

```java
MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
```

* 处理权限回调

```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
{
	MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```

授权成功以及失败调用的分支方法通过注解`PermissionGrant`和`PermissionDenied`进行标识，详细参考下面的例子或者sample。

## 例子

* in Activity:

```java
public class MainActivity extends AppCompatActivity
{

	private Button mBtnSdcard;
	private static final int REQUECT_CODE_SDCARD = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    mBtnSdcard = (Button) findViewById(R.id.id_btn_sdcard);
	    mBtnSdcard.setOnClickListener(new View.OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	            MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
	        }
	    });
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
	    MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}


	@PermissionGrant(REQUECT_CODE_SDCARD)
	public void requestSdcardSuccess()
	{
	    Toast.makeText(this, "GRANT ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
	}

	@PermissionDenied(REQUECT_CODE_SDCARD)
	public void requestSdcardFailed()
	{
	    Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
	}
}
```

* in Fragment:

```java

public class TestFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        view.findViewById(R.id.id_btn_contact).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MPermissions.requestPermissions(TestFragment.this, 4, Manifest.permission.WRITE_CONTACTS);
            }
        });

    }

    @PermissionGrant(4)
    public void requestContactSuccess()
    {
        Toast.makeText(getActivity(), "GRANT ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(4)
    public void requestContactFailed()
    {
        Toast.makeText(getActivity(), "DENY ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

```

