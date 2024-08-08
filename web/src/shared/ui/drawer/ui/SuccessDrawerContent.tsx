import {ReactNode} from 'react';

import {APP_ICON} from '@shared/ui';
import {APP_TEXT} from '@shared/config';

export enum SuccessDrawerType {
	create,
}

type Props = {
	type?: SuccessDrawerType;
	content?: ReactNode;

	preText?: string;
	primaryText?: string;
	postText?: string;
};

export function SuccessDrawerContent(props: Props) {
	const {type, preText, primaryText, postText} = props;

	return (
		<div className='flex flex-col items-center pb-4'>
			<div className='mb-4 h-10 w-10 pb-4 text-primary-violet'>{APP_ICON.check}</div>
			<div className='text-center font-semibold'>
				{preText}
				{primaryText && <span className='text-primary-violet'>{` ${primaryText} `}</span>}
				{type && SuccessDrawerType.create && APP_TEXT.createdSuccess}
				{postText}
			</div>
		</div>
	);
}
