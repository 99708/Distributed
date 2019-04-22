package org.xyq.cms.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.wicket.util.file.File;
import org.springframework.stereotype.Service;
import org.xyq.basic.model.Pager;
import org.xyq.basic.model.SystemContext;
import org.xyq.cms.dao.IAttachmentDao;
import org.xyq.cms.model.Attachment;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;


@Service("attachmentService")
public class AttachmentService implements IAttachmentService {
	
	public final static int IMG_WIDTH = 900;
	public final static int THUMBNAIL_WIDTH = 100;
	public final static int THUMBNAIL_HEIGHT = 80;
	private IAttachmentDao attachmentDao;
	public final static String UPLOAD_PATH="/upload/";
	
	public static void deleteAttachFiles(Attachment attachment){
		//删除硬盘上的文件
		String realPath = SystemContext.getRealPath();
		realPath += UPLOAD_PATH;
		new File(realPath+attachment.getNewName()).delete();
	}
	

	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	@Inject
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	@Override
	public void add(Attachment attachment, InputStream is) throws IOException {
		attachmentDao.add(attachment);
		saveFile(attachment, is);
	}
	
	private void saveFile(Attachment a, InputStream is) throws IOException{
		//进行文件的存储
		String realPath = SystemContext.getRealPath();
		String path = realPath+"/"+"resources/upload/";
		String thumbPath = realPath+"/"+"resources/upload/thumbnail/";
		File fp = new File(path);
		File tfp = new File(thumbPath);
		if(!fp.exists()) fp.mkdirs();
		if(!tfp.exists()) tfp.mkdirs();
		path += a.getNewName();
		thumbPath += a.getNewName();
		System.out.println( path );
		System.out.println( thumbPath );
		if(a.getIsImg() == 1){
			BufferedImage oldbi = ImageIO.read(is);
			int width = oldbi.getWidth();
			Builder<BufferedImage> bf = Thumbnails.of(oldbi);
			
			if (width > IMG_WIDTH) {
				bf.scale((double)IMG_WIDTH/(double)width);
			}else{
				bf.scale(1.0f);
			}
			bf.toFile(path);
			//缩略图的处理
			//1、将原图进行压缩
			BufferedImage tbi = Thumbnails.of(oldbi)
					.scale((double)(THUMBNAIL_WIDTH*1.2)/(double)width).asBufferedImage();
			//2、将原图进行切割
			Thumbnails.of(tbi).scale(1.0f)
			.sourceRegion(Positions.CENTER, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
			.toFile(thumbPath);
		}else {
			FileUtils.copyInputStreamToFile(is, new File(path));
		}
		
	}
	
	@Override
	public void delete(int id) {
		Attachment attachment = attachmentDao.load(id);
		attachmentDao.delete(id);
		deleteAttachFiles(attachment);
	}

	@Override
	public Attachment load(int id) {
		return attachmentDao.load(id);
	}

	@Override
	public Pager<Attachment> findNoUseAttachment() {
		return attachmentDao.findNoUseAttachment();
	}

	@Override
	public void clearNoUseAttachment() {
		attachmentDao.clearNoUseAttachment();
	}

	@Override
	public List<Attachment> listByTopic(Integer tid) {
		return attachmentDao.listByTopic(tid);
	}

	@Override
	public List<Attachment> listIndexPic(int num) {
		return attachmentDao.listIndexPic(num);
	}

	@Override
	public Pager<Attachment> findChannelPic(Integer cid) {
		return attachmentDao.findChannelPic(cid);
	}

	@Override
	public List<Attachment> listAttachByTopic(Integer tid) {
		return attachmentDao.listAttachByTopic(tid);
	}


	@Override
	public void updateIndexPic(int aid) {
		Attachment attachment = attachmentDao.load(aid);
		if (attachment.getIsIndexPic() == 0) {
			attachment.setIsIndexPic(1);
		} else {
			attachment.setIsIndexPic(0);
		}
		attachmentDao.update(attachment);
	}


	@Override
	public void updateAttachInfo(int aid) {
		Attachment attachment = attachmentDao.load(aid);
		if (attachment.getIsAttach() == 0) {
			attachment.setIsAttach(1);
		} else {
			attachment.setIsAttach(0);
		}
		attachmentDao.update(attachment);
	}

}
