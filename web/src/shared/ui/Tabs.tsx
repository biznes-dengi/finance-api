import {Tab} from '@headlessui/react';
import {Fragment, ReactElement, useState} from 'react';
import {cn} from '@shared/lib';

export type TabConfig = {
	label: string;
	path?: string;
};

type Props = {
	tabConfigs: TabConfig[];
	tabContentList?: ReactElement[];
	value?: number;
	handleChange?: (tabConfig: TabConfig, index: number, tabConfigs: TabConfig[]) => void;
	className?: string;
	isOutsideCard?: boolean;
};

export function Tabs(props: Props) {
	const {tabConfigs, tabContentList, value, handleChange, className, isOutsideCard} = props;

	const [tabValue, setTabValue] = useState(0);

	function handleChangeClick(index: number) {
		!value && setTabValue(index);
		handleChange?.(tabConfigs[index], index, tabConfigs);
	}

	return (
		<div role='tabs-wrapper' className={cn('bg-[#EDEFF2]', className, isOutsideCard && 'bg-transparent')}>
			<Tab.Group selectedIndex={value || tabValue} onChange={handleChangeClick}>
				<Tab.List>
					{tabConfigs.map(({label}, index) => {
						return (
							<Tab as={Fragment} key={label + index}>
								{({selected}) => (
									<button
										role='tab'
										className={cn(
											'px-4 py-1 outline-none',
											index + 1 !== tabConfigs.length && 'mr-3',
											selected ? 'rounded-2xl bg-white text-black' : 'text-primary-grey',
										)}
									>
										{label}
									</button>
								)}
							</Tab>
						);
					})}
				</Tab.List>
				{!!tabContentList?.length && (
					<Tab.Panels>
						{tabContentList.map((TabContent, index) => (
							<Tab.Panel key={index}>{TabContent}</Tab.Panel>
						))}
					</Tab.Panels>
				)}
			</Tab.Group>
		</div>
	);
}
