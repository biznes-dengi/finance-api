import {ReactNode, useRef, useState} from 'react';

import {APP_ICON, Button, ListItem, useDrawer} from '@shared/ui';

import {cn, isNumber, isString} from '@shared/lib';
import {APP_TEXT} from '@shared/config';

type Option = {
	name: string;
	currencySymbol: string;
	mask?: ReactNode;
};

type LeftLabel =
	| {
			balance?: number;
	  }
	| string;

type Props = {
	value: number | undefined;
	onChange: (value: number | undefined) => void;
	placeholder?: string;
	disabled?: boolean;

	className?: string;
	leftLabel: LeftLabel;
} & (
	| {
			option: Option;
			options?: never;
	  }
	| {
			option?: never;
			options: Option[];
	  }
);

export function CurrencyField(props: Props) {
	const {value, onChange, placeholder, disabled, className, option, options, leftLabel} = props;

	const inputRef = useRef<HTMLInputElement>(null);

	const [isError, setIsError] = useState(false);

	const {Drawer, openDrawer, closeDrawer} = useDrawer();

	function handleChange(value: string) {
		setIsError(false);
		onChange(value ? Number(value) : undefined);
	}

	const activeOption = option || options[0];

	return (
		<>
			<label className={cn('block rounded-2xl bg-input-grey p-4', className, isError && 'bg-[#FDE3E5]')}>
				<div className='flex items-center justify-between'>
					<div role='left-option' className='mr-4 flex items-center' onClick={openDrawer}>
						{activeOption.mask && <div className='mr-2 h-5 w-5 rounded-full'>{activeOption.mask}</div>}
						<div className='text-xl'>{activeOption.name}</div>
						{options?.length && <div className='ml-1 h-4 w-4 text-black'>{APP_ICON.selectArrow}</div>}
					</div>

					<input
						type='number'
						ref={inputRef}
						className={cn(
							'w-full bg-inherit text-right text-xl font-semibold caret-primary-violet outline-0',
							isError && 'caret-[#B51F2D]',
						)}
						value={value ?? ''}
						onChange={(event) => handleChange(event.target.value)}
						placeholder={String(placeholder || 0)}
						readOnly={disabled}
					/>

					<div className={cn('ml-2 text-xl font-semibold', !isNumber(value) && 'text-[#9CA3AF]')}>
						{activeOption.currencySymbol}
					</div>
				</div>

				<div className='mt-1 flex justify-between'>
					<div className={cn('text-sm font-light text-primary-grey', isError && 'text-[#B51F2D]')}>
						{getLeftLabel(leftLabel, activeOption)}
					</div>
					{isError && <div className='text-sm font-light text-[#B51F2D]'>exceeds balance (with small letter)</div>}
				</div>
			</label>

			{options && (
				<Drawer isCloseDisabled withOverlay={false}>
					<div className='mb-4' onClick={closeDrawer}>
						Close
					</div>

					{options.map((option) => (
						<Button key={option.name} onClick={() => alert('change active option')}>
							<ListItem name={option.name} />
						</Button>
					))}
				</Drawer>
			)}
		</>
	);
}

function getLeftLabel(leftLabel: LeftLabel, option: Option) {
	if (isString(leftLabel)) {
		return leftLabel;
	}

	if (leftLabel.balance) {
		return APP_TEXT.getBalance(leftLabel.balance, option?.currencySymbol ?? '');
	}
}

/**
 * make with spaces, when value is number
 * when first input value is 0, next value can be only "," or "."
 * can provide "e"
 * */

// When focus
// const isMobile = true;
// isMobile && inputRef.current?.setSelectionRange(value.length, value.length);

// when mappedValue is string
// <input value={textHelpers.getAmount(mappedValue.replace(/\s/g, ''))} />

// handleChange
// const mappedValue = value.replace(/\s/g, '');
// if ((isNumber(+mappedValue) && !Number.isNaN(+mappedValue)) || mappedValue.includes('e')) {
// 	onChange(value ? Number(value) : undefined);
// 	setIsError(false);
// }
