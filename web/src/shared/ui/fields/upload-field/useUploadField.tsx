import React, {ReactNode, useRef, useState} from 'react';
import {UploadField} from './ui/UploadField.tsx';

export type UploadFieldRef = {
	startUploading: () => void;
	abortUploading: () => void;
};

type UploadFieldWrapperProps = {
	onUpload: (data: unknown) => void;
	className?: string;
	children: ReactNode;
};

export type UploadFieldProps = UploadFieldWrapperProps & {
	setUploadProgressPercent: React.Dispatch<React.SetStateAction<number>>;
	setIsUploading: React.Dispatch<React.SetStateAction<boolean>>;
	setIsFileDragging: React.Dispatch<React.SetStateAction<boolean>>;
	isUploading: boolean;
};

export function useUploadField() {
	const [isFileDragging, setIsFileDragging] = useState(false);
	const [isUploading, setIsUploading] = useState(false);
	const [uploadProgressPercent, setUploadProgressPercent] = useState(0);

	const uploadRef = useRef<UploadFieldRef>(null);

	return {
		UploadField: ({children, ...restProps}: UploadFieldWrapperProps) => (
			<UploadField
				ref={uploadRef}
				{...{...restProps, setIsUploading, setUploadProgressPercent, setIsFileDragging, isUploading}}
			>
				{children}
			</UploadField>
		),
		startUploading: () => uploadRef.current?.startUploading(),
		abortUploading: () => uploadRef.current?.abortUploading(),
		isUploading,
		uploadProgressPercent,
		isFileDragging,
	};
}
