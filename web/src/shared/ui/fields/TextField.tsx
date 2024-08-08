import {cn} from '@shared/lib';
import {APP_ICON} from '@shared/ui/Icon.tsx';
import {useRef} from 'react';

type Props = {
	value: string;
	onChange: (value: string) => void;
	placeholder?: string;
	maxLength?: number;
	isSearch?: boolean;
};

/**
 * when isSearch and focused, pin search-input to the top with animation + cancel text at right
 */

export function TextField(props: Props) {
	const {value, onChange, placeholder, maxLength, isSearch} = props;

	const inputRef = useRef<HTMLInputElement>(null);

	function handleChange(value: string) {
		if (maxLength && value.length > maxLength) return;

		onChange(value);
	}

	return (
		<>
			<label className={cn('flex items-center rounded-2xl bg-secondary-grey p-4', isSearch && 'rounded-3xl p-1')}>
				{isSearch && <div className='mx-1 h-4 w-4 text-primary-grey'>{APP_ICON.search}</div>}

				<input
					ref={inputRef}
					className={cn('w-full bg-inherit caret-primary-violet outline-0')}
					value={value}
					onChange={(event) => handleChange(event.target.value)}
					placeholder={placeholder}
					type='text'
				/>

				{value && (
					<div
						onClick={() => onChange('')}
						className={cn(
							'ml-2 h-5 w-5 cursor-pointer rounded-full bg-primary-grey p-1 text-secondary-grey',
							isSearch && 'mx-1',
						)}
					>
						{APP_ICON.reset}
					</div>
				)}
			</label>

			{maxLength && (
				<div className={cn('flex cursor-default justify-end pr-4 pt-1 text-sm text-primary-grey')}>
					{value.length} / {maxLength}
				</div>
			)}
		</>
	);
}
