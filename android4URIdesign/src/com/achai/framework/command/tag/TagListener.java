package com.achai.framework.command.tag;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.achai.framework.app.UserApp;
import com.achai.framework.utils.AnalyseViewURI;

import android.R.string;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * 初始化 tag 的监听
 * 
 * @author tom_achai
 * 
 */
public class TagListener {
	private final Activity theAct;

	public TagListener(Activity act) {
		this.theAct = act;
	}

	public static class TagOnClickEvents {
		public OnClickListener clickEvent;
		public OnEditorActionListener edtEvent;
	}

	public void doViewClicked(View v) {
		String url = v.getTag().toString().trim();
		if (v != null && url != null) {
			// 对view 中tag 进行解析
			doExecUrl(v, url);
		}
	}

	protected void doExecUrl(View view, String url) {
		try {
			if (TagCheck.needSplit(url)) {
				String[] urls = url.split("\n");
				for (String u : urls) {
					execUrl(view, u);
				}
			} else {
				execUrl(view, url);
			}
		} catch (RuntimeException ex) {
			UserApp.curApp().showMessage(url + "url 解析错误");
		}
	}

	private void execUrl(View view, String u) {
		URI execUri = URI.create(u);
		if (execUri == null)
			return;
		String prefix = execUri.getScheme().toLowerCase();
		// 执行相关的的命令
		if (prefix.equals("cmd")) {
			execCmd(view, execUri);
		} else if (prefix.equals("act")) {
			execAct(execUri);
		}else if(prefix.equals("post")){
			execPost(view, execUri);
		}

	}
	/**
	 * 执行向服务器post 数据
	 * @param v
	 * @param u
	 */
	private void execPost(View v, URI u){
		//1,解析URI 里面的id 资源
		String query = u.getQuery();
		Map<String, String> mapId = TagCheck.getMap(query);
		List<View> paramView = new ArrayList<View>();
		for(String id: mapId.keySet()){
			//组装查询用的id
			String queryId = "res://id/"+mapId.get(id);
			int viewId = AnalyseViewURI.getResIdFromURL(queryId);
			View getView = theAct.findViewById(viewId);
			paramView.add(getView);
		}
		//获取参数值
		List<String> paramString = new ArrayList<String>();
		for (View view : paramView) {
			if(view instanceof TextView){
				String param = ((TextView) view).getText().toString();
				if(param != null){
					paramString.add(param);
				}
			}
		}
		//测试
		for (String string : paramString) {
			Log.d("tagpost", string);
		}
		
	}

	/**
	 * 执行命令操作
	 * 
	 * @param u
	 */
	private void execCmd(View v, URI u) {
		String type = u.getHost().toLowerCase();

		// 监控 watch
		if (type.equals("watch")) {
			// 用于观察view 中的变量改变情况,晚些在实现
			return;
		}

		// 结束当前view
		if (type.equals("finish")) {
			theAct.finish();
			return;
		}

		// 弹出提示
		if (type.equals("hint")) {
			String msg = u.getFragment();
			if (msg != null) {
				UserApp.showMessage(theAct, msg);
			}
			return;
		}
		// 重新读取
		if (type.equals("reload")) {
			return;
		}
		// 设置指定id view 的值
		// 例如 cmd://setview/?id=ok&attribute=text#hello,world!
		if (type.equals("setview")) {
			String query = u.getQuery().toLowerCase();
			String param = u.getFragment();
			View tagView = v;
			Map<String, String> qMap = TagCheck.getMap(query);
			if (qMap != null) {
				String idString = qMap.get("id");
				String attribute = qMap.get("attribute");
				if (idString != null) {
					int id = AnalyseViewURI.getResIdFromURL("res://id/"
							+ idString);
					tagView = theAct.findViewById(id);
				}
				// 这里应该 要判断param 是否为空!以后再补充!
				TagSetView.setView(tagView, attribute, param);
			}
			return;
		}
	}

	/**
	 * 执行activity 跳转动作
	 * act://com.achai@com.achai.main.TargetActivity/?id=12345&json=xxxxxxx
	 * 
	 * 1,完整包名 getHost();
	 * 
	 * @param u
	 */
	private void execAct(URI u) {
		// Intent
		Intent targetIntent = new Intent();

		// port
		int port = u.getPort();

		// 设定activity 的启动状态
		if (port != -1) {
			// 主要的启动模式
			switch (port) {
			// This flag can not be used when the caller is requesting a result
			// from the activity being launched.
			case 1:
				targetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				break;
			// clear top
			// 如果启动了A,B,C,D ,D start 了 B 那么在栈里面就只有
			// A,B
			case 2:
				targetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				break;
			// If set, the activity will not be launched if it is already
			// running at the top of the history stack.
			case 3:
				targetIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				break;
			default:
				break;
			}
		}
		// 目标activity 包名
		String targetPackageName = u.getUserInfo();
		// 目标activity 的类名
		String targetClassName = u.getHost();
		//
		ComponentName cn;
		if (targetClassName != null) {
			// 用指定包的方式启动跳转activity
			if (targetPackageName != null) {
				cn = new ComponentName(targetPackageName, targetClassName);
				targetIntent.setComponent(cn);
			} else {
				cn = new ComponentName(UserApp.curApp().getCurPackageName(),
						targetClassName);
				targetIntent.setComponent(cn);
			}
		}
		Bundle bundle = new Bundle();
		// 进行数据绑定Bundle
		String query = u.getQuery();
		if (query != null) {
			Map<String, String> bundleMap = TagCheck.getMap(query);

			for (String key : bundleMap.keySet()) {
				bundle.putString(key, bundleMap.get(key));
			}
			targetIntent.putExtras(bundle);
		}

		// 执行跳转
		theAct.startActivity(targetIntent);

	}

}
