package com.ego.ext.weixin.mp.model.massmsg;

import java.util.HashMap;
import java.util.Map;

public class PreviewMusicMassMsg  extends PreviewMassMsg {

	private Map<String, String> music;

	public PreviewMusicMassMsg(String media_id) {
		super();
		music = new HashMap<String, String>();
		music.put("media_id",media_id);
		super.msgtype = "music";
	}

	public Map<String, String> getMusic() {
		return music;
	}

	public void setMusic(Map<String, String> music) {
		this.music = music;
	}




}
