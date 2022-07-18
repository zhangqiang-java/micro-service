package com.zq.cloud.file.facade;

import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.dto.result.SingleResult;
import com.zq.cloud.file.facade.dto.FileServiceQueryDto;
import com.zq.cloud.file.facade.dto.FileServiceResult;
import com.zq.cloud.file.facade.dto.FileServiceUploadRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "${cloud-file.service-id:cloud-file}", path = "/service/file")
public interface FileClient {


    @PostMapping("/findFile")
    SingleResult<FileServiceResult> findFile(@RequestBody @Valid FileServiceQueryDto queryDto);


    @PostMapping("/upload")
    ResultBase<Void> upload(@RequestBody @Valid FileServiceUploadRequestDto uploadRequestDto);


    @PostMapping("/delete/{fileId}")
    ResultBase<Void> delete(@PathVariable("fileId") Long fileId);

}
