package org.xyq.cms.dwr;

public interface IDwrService {
		/**
		 * 添加GroupChannel对象
		 * @param group
		 * @param channel
		 */
		public void addGroupChannel(int gid,int cid);
		
		/**
		 * 删除用户栏目
		 * @param gid
		 * @param cid
		 */
		public void deleteGroupChannel(int gid,int cid);
		/**
		 * 更新是否是主页图片
		 * @param aid
		 */
		public void updateIndexPic(int aid);
		/**
		 * 更新是否是附件信息
		 * @param aid
		 */
		public void updateAttachInfo(int aid);
		/**
		 * 删除附件
		 * @param id
		 */
		public void deleteAttach(int id);
		
}
