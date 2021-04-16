/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jvfast.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * 文件上传工具类
 *
 * @author geekidea
 * @date 2019/8/21
 * @since 1.2.1-RELEASE
 */
@Slf4j
public final class FileUploadUtil {

    /**
     * 上传文件，默认文件名格式，yyyyMMddHHmmssS
     *
     * @param uploadPath
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public static String upload(MultipartFile multipartFile, String uploadPath) throws Exception {
        return upload(multipartFile, uploadPath, new DefaultUploadFileNameHandleImpl());
    }

    /**
     * 上传文件
     *
     * @param multipartFile        上传文件
     * @param uploadPath           上传目录
     * @param uploadFileNameHandle 回调
     * @return
     * @throws Exception
     */
    public static String upload(MultipartFile multipartFile, String uploadPath, UploadFileNameHandle uploadFileNameHandle) throws Exception {
        // 获取输入流
        String originalFilename = multipartFile.getOriginalFilename();
        InputStream inputStream = multipartFile.getInputStream();
        File saveDir = Paths.get(uploadPath).toFile();
        // 判断目录是否存在，不存在，则创建，如创建失败，则抛出异常
        if (!saveDir.exists()) {
            boolean flag = saveDir.mkdirs();
            if (!flag) {
                throw new RuntimeException("创建" + saveDir + "目录失败！");
            }
        }

        String saveFileName;
        if (uploadFileNameHandle == null) {
            saveFileName = new DefaultUploadFileNameHandleImpl().handle(multipartFile, originalFilename);
        } else {
            saveFileName = uploadFileNameHandle.handle(multipartFile, originalFilename);
        }

        File saveFile = new File(saveDir, saveFileName);
        log.info("保存文件到目录: {}", saveFile.getAbsolutePath());
        FileUtil.writeFromStream(inputStream, saveFile);
        // 保存oss文件到服务器指定路径
        return saveFileName;
    }


    public interface UploadFileNameHandle {
        String handle(MultipartFile multipartFile, String originalFilename);
    }

    public static class DefaultUploadFileNameHandleImpl implements UploadFileNameHandle {

        @Override
        public String handle(MultipartFile multipartFile, String originalFilename) {
            // 文件后缀
            String fileNameWithoutSuffix = IdUtil.fastSimpleUUID();
            String fileExtension = FileUtil.extName(originalFilename);
            // 这里可自定义文件名称，比如按照业务类型/文件格式/日期
            // 此处按照文件日期存储
            if (StrUtil.isEmpty(fileExtension)) {
                String contentType = multipartFile.getContentType();
                if (StrUtil.isNotEmpty(contentType)) {
                    if (contentType.startsWith("image/jpeg") || contentType.startsWith("image/png")) {
                        fileExtension = "png";
                    }
                }
            }
            String fileName = fileNameWithoutSuffix + StrUtil.DOT + fileExtension;
//            String fileNameWithoutSuffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssS"));
            return fileName;
        }
    }


    public static UploadFileType getUploadFileType(String originalFileName) {
        UploadFileType findMatchedType = Arrays.stream(UploadFileType.values()).filter(uploadFileType -> {
            List<String> sufffixs = uploadFileType.getSufffix();
            String extName = FileUtil.extName(originalFileName);
            return sufffixs.contains(extName);
        }).findFirst().orElse(UploadFileType.OTHER);
        return findMatchedType;
    }

    public enum UploadFileType {
        /**
         * 上传文件类型
         */
        IMG("png", "jpg", "jpeg", "gif", "bmp", "svg", "webp"),
        DOC("doc", "docx", "pdf", "xls", "xlsx", "ppt", "pptx"),
        OTHER("others");


        private List<String> suffix;

        UploadFileType(String... suffix) {
            this.suffix = Arrays.asList(suffix);
        }

        public List<String> getSufffix() {
            return suffix;
        }
    }

}
