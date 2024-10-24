## COLORS
BLUE = \033[34m
CYAN = \033[36m
GREEN = \033[32m
RESET = \033[0m
## CONFIGURATION PROPERTIES
PACKAGE_FLAG = runner/target/package.done
JAR_LOCATION = runner/target/runner-*.jar
CONFIG = runner/src/main/resources/application.yaml
## CLEAN STEP
clean:
	@echo "[$(BLUE)MAKEFILE$(RESET)] $(CYAN)CLEANING TARGET...$(RESET)"
	@mvn clean
	@rm -f $(PACKAGE_FLAG)
	@echo "[$(BLUE)MAKEFILE$(RESET)] $(GREEN)PROJECT CLEANED$(RESET)"
## PACKAGE STEP
package:
	@echo "[$(BLUE)MAKEFILE$(RESET)] $(CYAN)PACKAGING PROJECT...$(RESET)"
	@mvn clean package -DskipTests
	@touch $(PACKAGE_FLAG)
	@echo "[$(BLUE)MAKEFILE$(RESET)] $(GREEN)PACKAGING COMPLETED$(RESET)"
## TEST STEP
test: $(PACKAGE_FLAG)
	@echo "[$(BLUE)MAKEFILE$(RESET)] $(CYAN)RUNNING TESTS...$(RESET)"
	@mvn test
	@echo "[$(BLUE)MAKEFILE$(RESET)] $(GREEN)TESTS COMPLETED$(RESET)"
## APPLICATION EXECUTION STEP
start: $(CONFIG) $(PACKAGE_FLAG)
	@echo "[$(BLUE)MAKEFILE$(RESET)] $(CYAN)RUNNING APPLICATION...$(RESET)"
	@java -jar $(JAR_LOCATION) -Dspring.config.location=$(CONFIG)
## PREPARATION STEP (CONTAINS: clean, package and test steps)
prep: clean package test