import {useState} from 'react';
import {useNavigate} from 'react-router-dom';

import {
	APP_ICON,
	Box,
	Button,
	BUTTON_TYPE,
	CurrencyField,
	PageHeader,
	Select,
	Spinner,
	Stepper,
	TextField,
	useDrawer,
	useUploadField,
} from '@shared/ui';

import {APP_PATH, APP_TEXT} from '@shared/config';
import {CURRENCY} from '@shared/constants';
import {cn} from '@shared/lib';

const hints = ['Mustang', 'House', 'Guitar', 'Maldives', 'TV', 'iPhone 17', 'Book'];

const currencyOptions = [
	{description: 'PLN', symbol: 'zł', name: 'Polish Zloty', value: CURRENCY.PLN},
	{description: 'USD', symbol: '$', name: 'US Dollar', value: CURRENCY.USD},
	{description: 'BYN', symbol: 'byn', name: 'BLR rubel', value: CURRENCY.BYN},
	{description: 'EUR', symbol: 'eur', name: 'Euro', value: CURRENCY.EUR},
	{description: 'GBP', symbol: 'gbp', name: 'British pound', value: CURRENCY.GBP},
];

const initialStepIndex = 0;
const initialName = '';
const initialCurrencyValue = null;
const initialTargetAmount = undefined;

export function GoalCreatePage() {
	const navigate = useNavigate();

	const [activeStepIndex, setActiveStepIndex] = useState(initialStepIndex);

	/** Form state */
	const [name, setName] = useState(initialName);
	const [currencyValue, setCurrencyValue] = useState<CURRENCY | null>(initialCurrencyValue);
	const selectedCurrencyOption = currencyOptions.find((option) => option.value === currencyValue);
	const [targetAmount, setTargetAmount] = useState<number | undefined>(initialTargetAmount);

	function handleCurrencyValueChange(value: CURRENCY) {
		setCurrencyValue(value);
		setTargetAmount(initialTargetAmount);
	}

	const {openDrawer, Drawer, SuccessDrawerContent} = useDrawer();

	const {UploadField, startUploading, abortUploading, uploadProgressPercent, isUploading, isFileDragging} =
		useUploadField();

	const Header = (
		<PageHeader
			handleBackButtonClick={activeStepIndex === 0 ? undefined : () => setActiveStepIndex(activeStepIndex - 1)}
			backPath={APP_PATH.root}
		/>
	);

	return (
		<>
			{activeStepIndex === initialStepIndex ? (
				<UploadField onUpload={alert}>
					<div
						className={cn(
							'flex h-[290px] flex-col items-end justify-between rounded-b-2xl bg-secondary-grey',
							isUploading && 'bg-secondary-grey',
						)}
					>
						{Header}

						{isFileDragging && <div className='h-10 w-10 self-center text-primary-violet'>{APP_ICON.uploadImage}</div>}
						{isUploading && (
							<div className='cursor-default self-center text-center'>
								<div className='mb-4 font-semibold text-primary-violet'>{uploadProgressPercent}%</div>
								<div className='cursor-pointer text-sm underline hover:text-primary-violet' onClick={abortUploading}>
									Cancel uploading
								</div>
							</div>
						)}

						<div
							className='z-10 mb-4 mr-4 flex h-10 w-10 items-center justify-center rounded-full bg-primary-violet text-white shadow-[0_0_0_4px_white_inset]'
							onClick={startUploading}
						>
							{!isUploading ? APP_ICON.camera : <Spinner className='z-20 h-5 w-5' />}
						</div>
					</div>
				</UploadField>
			) : (
				Header
			)}

			<div className='flex-grow'>
				<Stepper
					activeStepIndex={activeStepIndex}
					steps={[
						<>
							<Box withBaseHorizontal>
								<TextField value={name} onChange={setName} maxLength={25} placeholder={APP_TEXT.goalName} />
							</Box>
							{!name && (
								<Box withBaseHorizontal className={cn('flex flex-wrap gap-2 pt-4')}>
									{hints.map((hint, index) => (
										<div
											key={hint + index}
											className={cn('w-fit rounded-2xl bg-secondary-grey px-2 py-0.5 text-sm')}
											onClick={() => setName(hint)}
										>
											{hint}
										</div>
									))}
								</Box>
							)}
						</>,
						<Box key={activeStepIndex} withBaseHorizontal>
							<Select options={currencyOptions} onChange={handleCurrencyValueChange} value={currencyValue} />
						</Box>,
						<Box key={activeStepIndex} withBaseHorizontal>
							<CurrencyField
								value={targetAmount}
								onChange={setTargetAmount}
								option={{
									name: selectedCurrencyOption?.description ?? '',
									currencySymbol: selectedCurrencyOption?.symbol ?? '',
									mask: <div className='h-full rounded-full bg-primary-grey' />,
								}}
								leftLabel={APP_TEXT.amount}
							/>
						</Box>,
					]}
				/>
			</div>

			<Box withBaseHorizontal withMediumVertical>
				<Button
					onClick={activeStepIndex === 2 ? () => openDrawer() : () => setActiveStepIndex(activeStepIndex + 1)}
					type={BUTTON_TYPE.primary}
					disabled={(() => {
						if (activeStepIndex === 0) return name === initialName;
						if (activeStepIndex === 1) return currencyValue === initialCurrencyValue;
						if (activeStepIndex === 2) return targetAmount === initialTargetAmount;
					})()}
				>
					{activeStepIndex === 2 ? APP_TEXT.create : APP_TEXT.continue}
				</Button>
			</Box>

			<Drawer afterAutoCloseAction={() => navigate(APP_PATH.goalDetails)} isCloseDisabled>
				<SuccessDrawerContent preText={APP_TEXT.goal} primaryText={name} postText={APP_TEXT.createdSuccess} />
			</Drawer>
		</>
	);
}
