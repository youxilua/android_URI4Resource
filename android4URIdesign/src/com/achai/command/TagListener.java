package com.achai.command;

import java.net.URI;
import java.util.Map;

import com.achai.app.UserApp;
import com.achai.utils.AnalyseViewURI;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView.OnEditorActionListener;

public class TagListener {
	private final Activity theAct;

	public TagListener(Activity act) {
		this.theAct = act;
	}

	public static class TagOnClickEvents {
		public OnClickListener clickEvent;
		public OnEditorActionListener edtEvent;
	}

	protected void doViewClicked(View v) {
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
		String prefix = execUri.getScheme();
		// 执行相关的的命令
		if (prefix.equals("cmd")) {
			execCmd(view, execUri);
		} else if (prefix.equals("act")) {
			execAct(execUri);
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
	 * 执行动作操作
	 * 
	 * @param u
	 */
	private void execAct(URI u) {

	}

}
