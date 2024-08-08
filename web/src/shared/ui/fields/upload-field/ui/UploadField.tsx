import React, {ChangeEvent, forwardRef, useId, useImperativeHandle, useRef} from 'react';

import {UploadFieldProps, UploadFieldRef} from '@shared/ui';
import {cn} from '@shared/lib';

export const UploadField = forwardRef<UploadFieldRef, UploadFieldProps>((props, ref) => {
	const {onUpload, children, className, setIsFileDragging, setIsUploading, setUploadProgressPercent, isUploading} =
		props;

	const abortUploadingAPIRef = useRef<() => void>();
	const fileInputRef = useRef<HTMLInputElement>(null);

	const abortUploading = () => {
		// Если у юзера медленный инет и долго грузится фотка, тогда он сможет отменить загрузку

		abortUploadingAPIRef.current?.();
		setIsUploading(false);
		setUploadProgressPercent(0);
	};

	const handleFileSend = (file: File) => {
		if (isUploading || !file) return;

		setIsUploading(true);

		onUpload(file);

		setIsUploading(false);

		// For integration with BE
		// type T = any;

		// const url = '';
		// const uploadingPromise = upload<T>(file, url, {onProgress: setProgress});
		// abortUploadingAPIRef.current = uploadingPromise.abort;
		//
		// uploadingPromise
		// 	.then(onUpload)
		// 	.catch(() => {})
		// 	.finally(() => {
		// 		setIsUploading(false);
		// 		setProgress(0);
		// 	}); // Хоть ошибка, хоть успех - сбрасываем загрузку и прогресс
	};
	const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
		if (!event.target.files?.length) return;
		handleFileSend(event.target.files[0]);
	};

	const onDragOver = (e: React.DragEvent<HTMLElement>) => {
		// Вызывается когда перемещаем файл. Нужен чтоб поменять UI, который покажет, что сюда можно дропать файлы

		if (isUploading) return;

		e.preventDefault();
		setIsFileDragging(true);
	};
	const onDragLeave = (e: React.DragEvent<HTMLElement>) => {
		// Вызывается когда мышь с файлом покинула компонент. Нужен для сброса отображения drop.

		if (isUploading) return;

		e.preventDefault();
		setIsFileDragging(false);
	};
	const onDrop = (e: React.DragEvent<HTMLElement>) => {
		// Вызывается когда роняем сюда файлы

		if (isUploading) return;

		e.preventDefault();

		const droppedFile = e.dataTransfer.files[0];
		setIsFileDragging(false);

		handleFileSend(droppedFile);
	};

	useImperativeHandle(ref, () => ({
		startUploading: () => fileInputRef.current?.click(),
		abortUploading,
	}));

	// Хорошая практика связывать label и input
	const id = useId();

	return (
		<div
			// uploadWrapper нужен, чтобы блокировать нажатие на input absolute лоадингом
			role='uploadWrapper'
			className={cn('relative', className)}
			onDrop={onDrop}
			onDragOver={onDragOver}
			onDragLeave={onDragLeave}
		>
			{children}

			<label htmlFor={id}>
				<input ref={fileInputRef} type='file' className='hidden' onChange={handleFileChange} id={id} />
			</label>
		</div>
	);
});
