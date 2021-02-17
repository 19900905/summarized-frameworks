package org.zxd;

import org.junit.Ignore;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
@Ignore
public class RollbackIntegrationTest extends BaseIntegrationTest {
}
