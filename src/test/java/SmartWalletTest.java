import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SmartWalletTest {

    private SmartWallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new SmartWallet("Ella", "Standard");
    }

    @Test
    void shouldDepositValidAmount() {
        assertTrue(wallet.deposit(50));
        assertEquals(50, wallet.getBalance());
    }

    @Test
    void shouldApplyCashbackWhenDepositGreaterThan100() {
        // 200 + (200 * 1%) = 202
        assertTrue(wallet.deposit(200));
        assertEquals(202, wallet.getBalance());
    }

    @Test
    void shouldNotApplyCashbackAtExactly100() {
        // 100 no aplica cashback
        assertTrue(wallet.deposit(100));
        assertEquals(100, wallet.getBalance());
    }

    @Test
    void shouldRejectNegativeDeposit() {
        assertFalse(wallet.deposit(-10));
        assertEquals(0, wallet.getBalance());
    }

    @Test
    void shouldRejectZeroDeposit() {
        assertFalse(wallet.deposit(0));
        assertEquals(0, wallet.getBalance());
    }

    @Test
    void shouldAllowExactLimit5000() {
        // deposit(4900) → 4900 + cashback(49) = 4949
        wallet.deposit(4900);
        // Depósito final ≤ 100 (sin cashback): 4949 + 51 = 5000 exacto 
        assertTrue(wallet.deposit(51));
        assertEquals(5000, wallet.getBalance());
    }

    @Test
    void shouldRejectDepositThatExceedsLimit() {
        wallet.deposit(4900);           // 4900 + 49 cashback = 4949
        // 4949 + 52 = 5001 > 5000 → rechazado
        assertFalse(wallet.deposit(52));
        assertEquals(4949, wallet.getBalance());
    }

    @Test
    void shouldRejectDepositWithCashbackThatExceedsLimit() {
        // 5000 + cashback(50) = 5050 > 5000 → rechazado
        assertFalse(wallet.deposit(5000));
        assertEquals(0, wallet.getBalance());
    }

    @Test
    void shouldWithdrawValidAmount() {
        // deposit(500) → 500 + cashback(5) = 505
        wallet.deposit(500);
        assertTrue(wallet.withdraw(200));
        assertEquals(305, wallet.getBalance());
    }

    @Test
    void shouldRejectWithdrawWithInsufficientFunds() {
        wallet.deposit(100);
        assertFalse(wallet.withdraw(200));
        assertEquals(100, wallet.getBalance());
    }

    @Test
    void shouldMarkInactiveWhenBalanceBecomesZero() {
        wallet.deposit(100);
        assertTrue(wallet.withdraw(100));
        assertEquals(0, wallet.getBalance());
        assertEquals("Inactiva", wallet.getStatus());
    }

    @Test
    void shouldRejectNegativeWithdraw() {
        wallet.deposit(100);
        assertFalse(wallet.withdraw(-5));
        assertEquals(100, wallet.getBalance());
    }

    @Test
    void shouldRejectZeroWithdraw() {
        wallet.deposit(100);
        assertFalse(wallet.withdraw(0));
        assertEquals(100, wallet.getBalance());
    }
}